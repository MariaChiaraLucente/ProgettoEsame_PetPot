package com.example.progettoesame_petpot.Login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.progettoesame_petpot.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceConnected(navController: NavHostController) {
    val image: Painter = painterResource(R.drawable.dispenser)
    var dispenser_name by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF5576B4)),
        contentAlignment = Alignment.Center
    ) {
        for (i in 1..3) {
            Box(
                modifier = Modifier
                    .size(100.dp * i)
                    .background(Color.Blue.copy(alpha = 0.3f / i), CircleShape)
            )
        }
        Image(
            painter = image,
            contentDescription = "Dispenser",
            modifier = Modifier.size(300.dp)
        )

        Text(
            text = "Dispenser connected!",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 22.sp,
            modifier = Modifier.padding(top = 200.dp).align(Alignment.TopCenter)
        )

        OutlinedTextField(
            value = dispenser_name,
            onValueChange = { dispenser_name = it },
            placeholder = {
                Text(
                    text = "Dispenser name",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 405.dp)
                .width(190.dp)
                .height(50.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFF8AA2CA),
            )
        )

        Button(
            onClick = {
                val db = Firebase.database.reference
                val name = mapOf("dispenser_name" to dispenser_name)
                db.child("name").push().setValue(name)
                    .addOnSuccessListener { /* Registration successful */ }
                    .addOnFailureListener { /* Registration failed */ }

                navController.navigate("caricamento")
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8)),
            modifier = Modifier.align(Alignment.Center).padding(top = 560.dp).width(150.dp).height(45.dp),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text("Next" , color = Color.White, fontSize = 16.sp)
        }


    }
}