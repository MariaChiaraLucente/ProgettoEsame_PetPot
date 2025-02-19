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

        profileRepository.getFoodLevels(userId, { storage, bowl ->

            viewModelScope.launch {
                val newBowlLevel: Float
                val newStorage: Float

                when {
                    amount > storage -> {
                        newBowlLevel = (bowl + storage).coerceAtMost(bowlCapacity)
                        newStorage = 0f
                    }
                    bowl + amount > bowlCapacity -> {
                        val maxPossible = bowlCapacity - bowl
                        newBowlLevel = bowl + maxPossible
                        newStorage = storage - maxPossible
                    }
                    else -> {
                        newBowlLevel = bowl + amount
                        newStorage = storage - amount
                    }
                }

                println("✅ Nuovi valori -> Bowl: $newBowlLevel g, Storage: $newStorage g")

                // ✅ Salva in Firebase
                profileRepository.saveFoodLevels(userId, newStorage, newBowlLevel)
                loadFoodLevels(userId)
            }
        }, { error ->
            println(error)
        })
    }


    fun updateTotalFood(userId: String, newTotal: Float) {
        _totalFoodStorage.value = newTotal
        profileRepository.saveFoodLevels(userId, newTotal, _bowlLevel.value)
    }
}
