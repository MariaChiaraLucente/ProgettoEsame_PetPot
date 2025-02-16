package com.example.progettoesame_petpot.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.progettoesame_petpot.model.PetPotModel
import com.example.progettoesame_petpot.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: PetPotModel = PetPotModel()) : ViewModel() {

    var dogProfile by mutableStateOf(User())
        private set

    var isLoading by mutableStateOf(true)
    var errorMessage by mutableStateOf<String?>(null)

    fun loadProfile(userId: String) {
        isLoading = true
        profileRepository.getDogProfile(
            userId,
            onSuccess = {
                dogProfile = it
                isLoading = false
            },
            onFailure = {
                errorMessage = it
                isLoading = false
            }
        )
    }

    fun updateProfile(userId: String, newProfile: User) {
        profileRepository.updateDogProfile(
            userId, newProfile,
            onSuccess = { dogProfile = newProfile },
            onFailure = { errorMessage = it }
        )
    }

    fun getProfile(): User? {
        return profileRepository.getCurrentUser()
    }

    fun logout() {
        profileRepository.clear()
    }

    private val _totalFoodStorage = MutableStateFlow(1000f) // Default: 1000g
    val totalFoodStorage: StateFlow<Float> = _totalFoodStorage

    private val _bowlLevel = MutableStateFlow(0f) // Default: 0g
    val bowlLevel: StateFlow<Float> = _bowlLevel

    private val bowlCapacity = 50f // Massimo della ciotola 50g

    fun loadFoodLevels(userId: String) {
        profileRepository.getFoodLevels(userId, { storage, bowl ->
            _totalFoodStorage.value = storage
            _bowlLevel.value = bowl
        }, { error ->
            println(error)
        })
    }

    fun dispenseFood(userId: String, amount: Int) {
        val currentStorage = _totalFoodStorage.value
        val currentBowl = _bowlLevel.value

        viewModelScope.launch {
            when {
                amount > currentStorage -> {
                    _bowlLevel.value = currentBowl + currentStorage
                    _totalFoodStorage.value = 0f
                }
                currentBowl + amount > bowlCapacity -> {
                    val maxPossible = bowlCapacity - currentBowl
                    _bowlLevel.value = currentBowl + maxPossible
                    _totalFoodStorage.value = currentStorage - maxPossible
                }
                else -> {
                    _bowlLevel.value = currentBowl + amount
                    _totalFoodStorage.value = currentStorage - amount
                }
            }

            // ✅ Salva i nuovi livelli in Firebase
            profileRepository.saveFoodLevels(userId, _totalFoodStorage.value, _bowlLevel.value)
        }
    }
}
