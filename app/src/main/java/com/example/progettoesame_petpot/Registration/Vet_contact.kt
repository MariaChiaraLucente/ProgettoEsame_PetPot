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
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterial3Api

@Composable
fun VetContact(navController: NavHostController) {
    var chosenPage by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var favorite_food by remember { mutableStateOf("") }
    var favorite_toy by remember { mutableStateOf("") }
    var state_of_health by remember { mutableStateOf("") }
    var allergies_or_intolerances by remember { mutableStateOf("") }
    var vet_name by remember { mutableStateOf("") }
    var vet_phone by remember { mutableStateOf("") }
    var other_information by remember { mutableStateOf("") }

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
                value = vet_name,
                onValueChange = { vet_name = it },
                placeholder = { Text("Vet's name") },
                modifier = Modifier.width(300.dp),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
        item {
            OutlinedTextField(
                value = vet_phone,
                onValueChange = { vet_phone = it },
                placeholder = { Text("Vet's phone") },
                modifier = Modifier.width(300.dp),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    //val db = Firebase.database.reference
                    //val user = mapOf("username" to username, "password" to password)
                    //db.child("users").push().setValue(user)
                    //.addOnSuccessListener { /* Registration successful */ }
                    //.addOnFailureListener { /* Registration failed */ }


                    navController.navigate("caricamento/drawers")
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
