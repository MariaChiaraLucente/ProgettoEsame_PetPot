package com.example.progettoesame_petpot.Registration

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.progettoesame_petpot.R
import com.example.progettoesame_petpot.viewmodel.RegistrationViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registration(navController: NavHostController, registrationViewModel: RegistrationViewModel = viewModel()) {
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

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
            value = registrationViewModel.user.username,
            onValueChange = {
                registrationViewModel.user = registrationViewModel.user.copy(username = it)
            },
            placeholder = { Text("Username") },
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = registrationViewModel.user.password,
            onValueChange = {
                registrationViewModel.user = registrationViewModel.user.copy(password = it)
            },
            placeholder = { Text("Password") },
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White
            ),
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {
                        navController.navigate("An_bio1") // Vai alla prossima schermata
                    },
            colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8)),
            modifier = Modifier.width(180.dp).height(45.dp),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text("Submit", color = Color.White, fontSize = 16.sp)
            }
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = Color(0xFFCA413F),
                modifier = Modifier.padding(top = 8.dp)
            )
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