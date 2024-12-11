package com.example.progettoesame_petpot.registration_2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.progettoesame_petpot.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registration(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF5576B4))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image: Painter = painterResource(R.drawable.logo_petpot)
        Image(
            painter = image,
            contentDescription = "logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(220.dp)
                .height(220.dp)
                .padding(bottom = 16.dp) // Sposta il logo leggermente più in alto
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Register",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("Username") },
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password") },
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {
                val db = Firebase.database.reference
                val user = mapOf("username" to username, "password" to password)
                db.child("users").push().setValue(user)
                    .addOnSuccessListener { /* Registration successful */ }
                    .addOnFailureListener { /* Registration failed */ }
                navController.navigate("An_bio1")
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8)),
            modifier = Modifier.width(180.dp).height(45.dp),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text("Submit" , color = Color.White, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "I already have an account",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Login",
            color = Color(0xFF5A3679),
            style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.Underline),
            modifier = Modifier.padding(top = 8.dp).clickable { navController.navigate("login")},
            fontWeight = FontWeight.Bold,
        )
    }
}