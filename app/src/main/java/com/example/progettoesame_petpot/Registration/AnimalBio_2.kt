package com.example.progettoesame_petpot.Registration

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.progettoesame_petpot.viewmodel.RegistrationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterial3Api
@Composable
fun AnimalBio2(navController: NavHostController, registrationViewModel: RegistrationViewModel = viewModel()) {

    var breedError by remember { mutableStateOf<String?>(null) }
    var favoriteFoodError by remember { mutableStateOf<String?>(null) }
    var allergiesError by remember { mutableStateOf<String?>(null) }

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
                text = "Animal Bio",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp),
                fontWeight = FontWeight.Bold
            )
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }
        item {
            OutlinedTextField(
                value = registrationViewModel.user.breed,
                onValueChange = {
                    registrationViewModel.user = registrationViewModel.user.copy(breed = it)
                    breedError = if (it.isBlank()) "Please choose a breed" else null
                                },
                placeholder = { Text("Breed") },
                modifier = Modifier.width(300.dp),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )
            if (breedError != null) {
                Text(breedError!!, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp))
            }
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
        item {
            OutlinedTextField(
                value = registrationViewModel.user.favoriteFood,
                onValueChange = {
                    registrationViewModel.user = registrationViewModel.user.copy(favoriteFood = it)
                    favoriteFoodError = if (it.isBlank()) "Please choose a favorite food" else null
                                },
                placeholder = { Text("Favorite food") },
                modifier = Modifier.width(300.dp),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )
            if (favoriteFoodError != null) {
                Text(favoriteFoodError!!, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp))
            }
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }

        item {
            OutlinedTextField(
                value = registrationViewModel.user.allergies,
                onValueChange = {
                    registrationViewModel.user = registrationViewModel.user.copy(allergies = it)
                    allergiesError = if (it.isBlank()) "Please choose allergies or intolerances" else null
                                },
                placeholder = { Text("Allergies or intolerances") },
                modifier = Modifier.width(300.dp),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )
            if (allergiesError != null) {
                Text(allergiesError!!, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp))
            }
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }

        item {
            OutlinedTextField(
                value = registrationViewModel.user.others,
                onValueChange = {
                    registrationViewModel.user = registrationViewModel.user.copy(others = it)
                                },
                placeholder = { Text("Other information") },
                modifier = Modifier.width(300.dp),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )
        }
        item { Spacer(modifier = Modifier.height(55.dp)) }
        item {
            Button(
                onClick = {
                    if (registrationViewModel.user.breed.isBlank()) {
                        breedError = "Please choose a breed"
                    }
                    if (registrationViewModel.user.favoriteFood.isBlank()) {
                        favoriteFoodError = "Please choose a favorite food"
                    }
                    if (registrationViewModel.user.allergies.isBlank()) {
                        allergiesError = "Please choose allergies or intolerances"
                    }

                    if (allergiesError == null && favoriteFoodError == null && breedError == null) {
                        navController.navigate("VetContact") // ✅ Solo se non ci sono errori
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8)),
                modifier = Modifier.width(180.dp).height(45.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text("Register", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}