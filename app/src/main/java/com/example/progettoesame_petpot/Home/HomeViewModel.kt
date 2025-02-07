package com.example.progettoesame_petpot.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.progettoesame_petpot.model.PetPotModel
import com.example.progettoesame_petpot.model.User
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

    fun getProfile(): User {
        val user = profileRepository.getCurrentUser()

        if (user != null) {
            return user
        }

        throw IllegalStateException("User not found")
    }
}
