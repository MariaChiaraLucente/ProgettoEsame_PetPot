package com.example.progettoesame_petpot

import android.bluetooth.BluetoothAdapter
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.progettoesame_petpot.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                .clickable { navController.navigate("registration") },
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
            placeholder = { Text("Dispenser name")},
            modifier = Modifier.width(190.dp).align(Alignment.BottomCenter).padding(bottom = 190.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(Color.White, Color.White, Color.White, Color.White)
        )

        Button(
            onClick = {
                // Aggiungi il codice per salvare il nome del dispenser
                navController.navigate("registration")
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8)),
            modifier = Modifier.width(180.dp).height(45.dp),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text("Next" , color = Color.White, fontSize = 16.sp)
        }


    }
}