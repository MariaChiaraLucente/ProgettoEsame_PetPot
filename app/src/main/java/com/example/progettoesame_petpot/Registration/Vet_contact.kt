package com.example.progettoesame_petpot.Registration

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.progettoesame_petpot.viewmodel.RegistrationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterial3Api

@Composable
fun VetContact(navController: NavHostController, registrationViewModel: RegistrationViewModel = viewModel()) {
    var vetNameError by remember { mutableStateOf<String?>(null) }
    var vetPhoneError by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF5576B4))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
            text = "Vet Info",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        item {
            OutlinedTextField(
                value = registrationViewModel.user.vetName,
                onValueChange = {
                    registrationViewModel.user = registrationViewModel.user.copy(vetName = it)
                    vetNameError = if (it.isBlank()) "Please choose a vet name" else null
                },
                placeholder = { Text("Vet's name") },
                modifier = Modifier.width(300.dp),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )
            if (vetNameError != null) {
                Text(vetNameError!!, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp))
            }
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
        item {
            OutlinedTextField(
                value = registrationViewModel.user.vetPhone,
                onValueChange = {
                    registrationViewModel.user = registrationViewModel.user.copy(vetPhone = it)
                    vetPhoneError = if (it.isBlank()) "Please choose a vet phone number" else null
                },
                placeholder = { Text("Vet's phone") },
                modifier = Modifier.width(300.dp),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )
            if (vetPhoneError != null) {
                Text(vetPhoneError!!, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    registrationViewModel.completeRegistration(
                        onSuccess = { navController.navigate("caricamento/drawers") },
                        onFailure = { error -> Log.e("Button", "Error: $error") }
                    )
                },
                enabled = vetNameError == null && vetPhoneError == null &&  // 🔴 Disabilita se ci sono errori
                        registrationViewModel.user.vetName.isNotBlank() &&
                        registrationViewModel.user.vetPhone.isNotBlank(),
                colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8)),
                modifier = Modifier.width(180.dp).height(45.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text("Register", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}
