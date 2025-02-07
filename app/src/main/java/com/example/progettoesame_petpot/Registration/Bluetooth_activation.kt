package com.example.progettoesame_petpot.Registration

import android.bluetooth.BluetoothAdapter
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
fun BluetoothSearching(navController: NavHostController) {
    val image: Painter = painterResource(R.drawable.bluetooth)
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scope = rememberCoroutineScope()

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    //parte di codice che controlla lo stato del Bluetooth
    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
                if (bluetoothAdapter?.isEnabled == true) {
                    delay(200) // Aggiungi un ritardo di 2 secondi
                    Toast.makeText(navController.context, "Bluetooth enabled!", Toast.LENGTH_SHORT).show()
                    delay(1000) // Aggiungi un ritardo di 2 secondi
                    navController.navigate("device") // Vai alla schermata successiva
                    break
                }
                delay(1000) // Controlla lo stato del Bluetooth ogni secondo
            }
        }
    }

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
                    .scale(scale)
                    .background(Color.Blue.copy(alpha = 0.3f / i), CircleShape)
            )
        }
        Image(
            painter = image,
            contentDescription = "Bluetooth",
            modifier = Modifier.size(100.dp)
        )

        Text(
            text = "Activate Bluetooth to connect",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 137.dp).align(Alignment.BottomCenter)
            )
        Text(
            text = "Pet Pot to your dispenser",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 110.dp).align(Alignment.BottomCenter)
        )


    }
}