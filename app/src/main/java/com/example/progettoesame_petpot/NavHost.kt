package com.example.progettoesame_petpot

import HomePage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.progettoesame_petpot.Calendar.Components.Event
import com.example.progettoesame_petpot.Calendar.Components.NewEventScreen
import com.example.progettoesame_petpot.Home.Components.Drawers
import com.example.progettoesame_petpot.Home.componenti_fede.QuickFeed
import com.example.progettoesame_petpot.Login.BluetoothSearching
import com.example.progettoesame_petpot.Login.Caricamento
import com.example.progettoesame_petpot.Login.Device
import com.example.progettoesame_petpot.Login.DeviceConnected
import com.example.progettoesame_petpot.Login.Login
import com.example.progettoesame_petpot.registration_2.AnimalBio1
import com.example.progettoesame_petpot.registration_2.AnimalBio2
import com.example.progettoesame_petpot.registration_2.Registration
import com.example.progettoesame_petpot.registration_2.VetContact
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AppNavigation() {
    val navController = rememberNavController() // Inizializza il NavController
    val currentScreen = remember { mutableStateOf("Home") }
    val navControllerCalendar = rememberNavController()
    val events = remember { mutableStateListOf<Event>() }

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
        composable("caricamento/{destination}", arguments = listOf(navArgument("destination") { type = NavType.StringType })) { backStackEntry ->
            val destination = backStackEntry.arguments?.getString("destination") ?: "HomePage"
            Caricamento(navController, destination)
        }
        composable("Drawers") { Drawers(navController) }
        composable("QuickFeed") { QuickFeed(navController) }
        composable("HomePage") { HomePage(navController) }
        composable("An_bio1") { AnimalBio1(navController) }
        composable("An_bio2") { AnimalBio2(navController) }
        composable("VetContact") { VetContact(navController) }
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


/*@Composable
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
*/


