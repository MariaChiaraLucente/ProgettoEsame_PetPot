package com.example.progettoesame_petpot.Login

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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


@Composable
fun Device(navController: NavHostController) {
    val image: Painter = painterResource(R.drawable.dispenser)
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

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
            contentDescription = "Dispenser",
            modifier = Modifier.size(300.dp)
                .clickable { navController.navigate("device_connected") },
        )

        Text(
            text = "Select your dispenser",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 137.dp).align(Alignment.BottomCenter)
        )


    }
}