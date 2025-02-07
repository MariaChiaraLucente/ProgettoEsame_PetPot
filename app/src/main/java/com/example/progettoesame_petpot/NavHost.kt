package com.example.progettoesame_petpot

import HomePage
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.progettoesame_petpot.Calendar.Components.FeedCreationScreen
import com.example.progettoesame_petpot.Home.Components.Drawers
import com.example.progettoesame_petpot.Home.componenti_fede.QuickFeed
import com.example.progettoesame_petpot.Login.Caricamento
import com.example.progettoesame_petpot.Login.Login
import com.example.progettoesame_petpot.Registration.AnimalBio1
import com.example.progettoesame_petpot.Registration.AnimalBio2
import com.example.progettoesame_petpot.Registration.BluetoothSearching
import com.example.progettoesame_petpot.Registration.Device
import com.example.progettoesame_petpot.Registration.DeviceConnected
import com.example.progettoesame_petpot.Registration.Registration
import com.example.progettoesame_petpot.Registration.VetContact
import com.example.progettoesame_petpot.viewmodel.RegistrationViewModel
import com.example.progettoesame_petpot.viewmodel.ProfileViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AppNavigation() {
    val navController = rememberNavController() // Inizializza il NavController
    val currentScreen = remember { mutableStateOf("Home") }
    val navControllerCalendar = rememberNavController()
    val events = remember { mutableStateListOf<Event>() }
    val registrationViewModel: RegistrationViewModel = viewModel()
    val homeViewModel: ProfileViewModel = viewModel()


    NavHost(
        navController = navController,
        startDestination = "login" // Schermata iniziale
    ) {
        // Ogni composable riceve il parametro `navController`
        composable("bluetooth") { BluetoothSearching(navController) }
        composable("device_connected") { DeviceConnected(navController) }
        composable("device") { Device(navController) }
        composable("login") { Login(navController) }
        composable("registration") { Registration(navController, registrationViewModel) }
        composable("caricamento/{destination}", arguments = listOf(navArgument("destination") { type = NavType.StringType })) { backStackEntry ->
            val destination = backStackEntry.arguments?.getString("destination") ?: "HomePage"
            Caricamento(navController, destination)
        }
        composable("Drawers") { Drawers(navController, homeViewModel.getProfile().userId, homeViewModel) }
        composable("QuickFeed") { QuickFeed(navController) }
        composable("HomePage") { HomePage(navController) }
        composable("An_bio1") { AnimalBio1(navController, registrationViewModel) }
        composable("An_bio2") { AnimalBio2(navController, registrationViewModel) }
        composable("VetContact") { VetContact(navController, registrationViewModel) }
        composable("calendar") {
            CalendarScreen(navController, events)
        }
        composable(
            "newEvent/{selectedDay}/{selectedMonth}",
            arguments = listOf(
                navArgument("selectedDay") { type = NavType.IntType },
                navArgument("selectedMonth") { type = NavType.IntType }
            )
        }

        composable(
            route = "feedCreation/{startDay}/{endDay}/{startMonth}/{endMonth}",
            arguments = listOf(
                navArgument("startDay") { type = NavType.IntType },
                navArgument("endDay") { type = NavType.IntType },
                navArgument("startMonth") { type = NavType.IntType },
                navArgument("endMonth") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val startDay = backStackEntry.arguments?.getInt("startDay")
            val endDay = backStackEntry.arguments?.getInt("endDay")
            val startMonth = backStackEntry.arguments?.getInt("startMonth")
            val endMonth = backStackEntry.arguments?.getInt("endMonth")

            FeedCreationScreen(
                startDay = startDay ?: 0,
                endDay = endDay ?: 0,
                startMonth = startMonth ?: 0,
                endMonth = endMonth ?: 0,
                onSave = { navController.popBackStack() }
            )
        }

    }
}


