package com.example.progettoesame_petpot

import BluetoothSearching
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplicationpetpot.AnimalBio1
import com.example.myapplicationpetpot.AnimalBio2
import androidx.navigation.navArgument
import com.example.progettoesame_petpot.Calendar.Components.Event
import com.example.progettoesame_petpot.Calendar.Components.NewEventScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
/*@Composable
fun AppNavigation() {
    val navController = rememberNavController() // Inizializza il NavController
    val navControllerCalendar = rememberNavController()

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

        composable("calendar") { CalendarScreen(navController)  }
        composable(
            "newEvent/{selectedDay}",
            arguments = listOf(navArgument("selectedDay") { type = NavType.IntType })
        ) { backStackEntry ->
            val selectedDay = backStackEntry.arguments?.getInt("selectedDay")
            NewEventScreen(selectedDay = selectedDay ?: 0)
        }
    }}
    */

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Global state to manage events
    val events = remember { mutableStateListOf<Event>() }

    NavHost(
        navController = navController,
        startDestination = "calendar"
    ) {
        composable("calendar") {
            CalendarScreen(navController, events)
        }
        composable(
            "newEvent/{selectedDay}/{selectedMonth}",
            arguments = listOf(
                navArgument("selectedDay") { type = NavType.IntType },
                navArgument("selectedMonth") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val selectedDay = backStackEntry.arguments?.getInt("selectedDay") ?: 0
            val selectedMonth = backStackEntry.arguments?.getInt("selectedMonth") ?: 0
            NewEventScreen(selectedDay, selectedMonth, events, navController)
        }
    }
}



