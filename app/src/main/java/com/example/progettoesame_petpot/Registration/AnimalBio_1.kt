package com.example.progettoesame_petpot.Registration

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
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.progettoesame_petpot.R
import com.example.progettoesame_petpot.viewmodel.RegistrationViewModel

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalBio1(navController: NavHostController, registrationViewModel: RegistrationViewModel = RegistrationViewModel()) {
    val image1: Painter = painterResource(R.drawable.dog1)
    val image2: Painter = painterResource(R.drawable.dog2)
    val image3: Painter = painterResource(R.drawable.dog3)

    val imagebaby: Painter = painterResource(R.drawable.baby_dog)
    val imageyoung: Painter = painterResource(R.drawable.young_dog)
    val imageold: Painter = painterResource(R.drawable.old_dog)

    var selectedSize by remember { mutableStateOf("") }
    var selectedAge by remember { mutableStateOf("") }

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
            SizeOption(image1, 92, 39, "Small", selectedSize) { selectedSize = "Small"; registrationViewModel.user = registrationViewModel.user.copy(size = "Small") }
            SizeOption(image2, 104, 55, "Medium", selectedSize) { selectedSize = "Medium"; registrationViewModel.user = registrationViewModel.user.copy(size = "Medium") }
            SizeOption(image3, 117, 70, "Large", selectedSize) { selectedSize = "Large"; registrationViewModel.user = registrationViewModel.user.copy(size = "Large") }
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
            AgeOption(imagebaby, 92, 39, "0-3 years", selectedAge) { selectedAge = "0-3 years"; registrationViewModel.user = registrationViewModel.user.copy(age = "0-3 years") }
            AgeOption(imageyoung, 104, 55, "4-9 years", selectedAge) { selectedAge = "4-9 years"; registrationViewModel.user = registrationViewModel.user.copy(age = "4-9 years") }
            AgeOption(imageold, 117, 70, "> 10 years", selectedAge) { selectedAge = "> 10 years"; registrationViewModel.user = registrationViewModel.user.copy(age = "> 10 years") }
        }

        Spacer(modifier = Modifier.height(55.dp))
        Button(
            onClick = {
                navController.navigate("An_bio2")
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8)),
            modifier = Modifier.width(180.dp).height(45.dp),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text("Next", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun SizeOption(image: Painter, width: Int, imgWidth: Int, size: String, selectedSize: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(width.dp)
            .height(width.dp)
            .background(Color(0xFF456883), shape = RoundedCornerShape(20.dp))
            .border(BorderStroke(2.dp, if (selectedSize == size) Color.Yellow else Color(0xFF2E3EB8)), shape = RoundedCornerShape(20))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Image(
            painter = image,
            contentDescription = size,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(imgWidth.dp)
                .height(imgWidth.dp)
        )
        Text(text = size.first().toString(), color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AgeOption(image: Painter, width: Int, imgWidth: Int, age: String, selectedAge: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(width.dp)
            .height(width.dp)
            .background(Color(0xFF456883), shape = RoundedCornerShape(20.dp))
            .border(BorderStroke(2.dp, if (selectedAge == age) Color.Yellow else Color(0xFF2E3EB8)), shape = RoundedCornerShape(20))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Image(
            painter = image,
            contentDescription = age,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(imgWidth.dp)
                .height(imgWidth.dp)
        )
        Text(text = age, color = Color.White, fontSize = 14.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}