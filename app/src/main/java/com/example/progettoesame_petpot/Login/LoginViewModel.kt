package com.example.progettoesame_petpot.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.progettoesame_petpot.model.PetPotModel
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: PetPotModel = PetPotModel()) : ViewModel() {
    var username = ""
    var password = ""

    var loginMessage: String? = null

    fun login(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            Log.d("ViewModel", "Tentativo di login con username: $username")
            viewModelScope.launch {
                userRepository.loginUser(username, password,
                    onSuccess = {
                        loginMessage = "Login successful!"
                        onSuccess()
                    },
                    onFailure = { error ->
                        loginMessage = error
                        onFailure(error)
                    }
                )
            }
        } else {
            loginMessage = "⚠ Username or password cannot be empty! ⚠"
            onFailure("⚠ Username or password cannot be empty! ⚠")
        }
    }
}
