package com.example.myapplicationpetpot

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.progettoesame_petpot.R

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalBio1(navController: NavHostController) { //tra parentesi c'era navController: NavHostController
    var size by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    val image1: Painter = painterResource(R.drawable.dog1)
    val image2: Painter = painterResource(R.drawable.dog2)
    val image3: Painter = painterResource(R.drawable.dog3)

    val imagebaby: Painter = painterResource(R.drawable.baby_dog)
    val imageyoung: Painter = painterResource(R.drawable.young_dog)
    val imageold: Painter = painterResource(R.drawable.old_dog)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF5576B4))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Animal Bio",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "Select the size:",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(92.dp)
                    .height(92.dp)
                    .background(Color(0xFF456883), shape = RoundedCornerShape(20.dp))
                    .border(BorderStroke(2.dp, Color(0xFF2E3EB8)), shape = RoundedCornerShape(20))
                    .clickable { size = "Small" }
                    .padding(8.dp)
            ) {
                Image(
                    painter = image1,
                    contentDescription = "Small",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(39.dp)
                        .height(39.dp)
                )
                Text(text = "S", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(104.dp)
                    .height(104.dp)
                    .background(Color(0xFF456883), shape = RoundedCornerShape(20.dp))
                    .border(BorderStroke(2.dp, Color(0xFF2E3EB8)), shape = RoundedCornerShape(20))
                    .clickable { size = "Small" }
                    .padding(8.dp)
            ) {
                Image(
                    painter = image2,
                    contentDescription = "Medium",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(55.dp)
                        .height(55.dp)
                )
                Text(text = "M", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(117.dp)
                    .height(117.dp)
                    .background(Color(0xFF456883), shape = RoundedCornerShape(20.dp))
                    .border(BorderStroke(2.dp, Color(0xFF2E3EB8)), shape = RoundedCornerShape(20))
                    .clickable { size = "Small" }
                    .padding(8.dp)
            ) {
                Image(
                    painter = image3,
                    contentDescription = "Large",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                )
                Text(text = "L", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Select the age:",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(92.dp)
                    .height(92.dp)
                    .background(Color(0xFF456883), shape = RoundedCornerShape(20.dp))
                    .border(BorderStroke(2.dp, Color(0xFF2E3EB8)), shape = RoundedCornerShape(20))
                    .clickable { age = "0-3 years" }
                    .padding(8.dp)
            ) {
                Image(
                    painter = imagebaby,
                    contentDescription = "baby",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(47.dp)
                        .height(47.dp)
                )
                Text(text = "0-3 years", color = Color.White, fontSize = 14.sp)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(104.dp)
                    .height(104.dp)
                    .background(Color(0xFF456883), shape = RoundedCornerShape(20.dp))
                    .border(BorderStroke(2.dp, Color(0xFF2E3EB8)), shape = RoundedCornerShape(20))
                    .clickable { age = "4-9 years" }
                    .padding(8.dp)
            ) {
                Image(
                    painter = imageyoung,
                    contentDescription = "young",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(57.dp)
                        .height(57.dp)
                )
                Text(text = "4-9 years", color = Color.White, fontSize = 14.sp)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(117.dp)
                    .height(117.dp)
                    .background(Color(0xFF456883), shape = RoundedCornerShape(20.dp))
                    .border(BorderStroke(2.dp, Color(0xFF2E3EB8)), shape = RoundedCornerShape(20))
                    .clickable { age = "over 10 years" }
                    .padding(8.dp)
            ) {
                Image(
                    painter = imageold,
                    contentDescription = "old",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                )
                Text(text = "over 10 years", color = Color.White, fontSize = 14.sp)
            }
        }


        Spacer(modifier = Modifier.height(55.dp))
        Button(
            onClick = {
                //val db = Firebase.database.reference
                //val user = mapOf("username" to username, "password" to password)
                //db.child("users").push().setValue(user)
                //.addOnSuccessListener { /* Registration successful */ }
                //.addOnFailureListener { /* Registration failed */ }
                navController.navigate("An_bio2")
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8)),
            modifier = Modifier.width(180.dp).height(45.dp),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text("Next" , color = Color.White, fontSize = 16.sp)
        }
    }
}