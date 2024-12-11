package com.example.progettoesame_petpot

import BluetoothSearching
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplicationpetpot.AnimalBio1
import com.example.myapplicationpetpot.AnimalBio2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController() // Inizializza il NavController

    NavHost(
        navController = navController,
        startDestination = "bluetooth" // Schermata iniziale
    ) {
        // Ogni composable riceve il parametro `navController`
        composable("bluetooth") { BluetoothSearching(navController) }
        composable("device_connected") { DeviceConnected(navController) }
        composable("device") { Device(navController) }
        composable("login") { Login(navController) }
        composable("registration") { Registration(navController) }
        composable("caricamento") { Caricamento(navController) }
        composable("An_bio1") { AnimalBio1(navController) }
        composable("An_bio2") { AnimalBio2(navController) }

    }
}
