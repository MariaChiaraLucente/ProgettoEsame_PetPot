package com.example.progettoesame_petpot.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.progettoesame_petpot.model.PetPotModel
import com.example.progettoesame_petpot.model.User
import kotlinx.coroutines.launch

class RegistrationViewModel(private val userRepository: PetPotModel = PetPotModel()) : ViewModel() {

    var userId by mutableStateOf("")

    var user by mutableStateOf(User())

    var registrationMessage: String? = null

    fun completeRegistration(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        Log.d("ViewModel", "completeRegistration chiamata")
        if (user.username.isNotEmpty() && user.password.isNotEmpty() &&
            user.size.isNotEmpty() && user.age.isNotEmpty() &&
            user.breed.isNotEmpty() && user.favoriteFood.isNotEmpty() &&
            user.allergies.isNotEmpty() && user.vetName.isNotEmpty() && user.vetPhone.isNotEmpty()
        ) {
            Log.d("ViewModel", "Registrazione con tutti i dati in corso...")
            viewModelScope.launch {
                userRepository.registerUser(
                    user,
                    onSuccess = {
                        registrationMessage = "Registrazione completata con successo!"
                        onSuccess()
                    },
                    onFailure = { error ->
                        registrationMessage = error
                        onFailure(error)
                    }
                )
            }
        } else {
            Log.d("ViewModel", "Missing fields: username=${user.username}, password=${user.password}, size=${user.size}, age=${user.age}, breed=${user.breed}, favoriteFood=${user.favoriteFood}, allergies=${user.allergies}, vetName=${user.vetName}, vetPhone=${user.vetPhone}")
            registrationMessage = "Tutti i campi sono obbligatori!"
            onFailure("Tutti i campi sono obbligatori!")
        }
    }
}
