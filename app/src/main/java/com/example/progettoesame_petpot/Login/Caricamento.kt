package com.example.progettoesame_petpot.Login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.example.progettoesame_petpot.R

@Composable
fun Caricamento(navController: NavHostController) {

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(GifDecoder.Factory())
        }
        .build()

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("HomePage")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF33435F)),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.loading_dog)
                .build(),
            contentDescription = "Loading Animation",
            imageLoader = imageLoader,
            Modifier.size(300.dp)
        )
    }
}



