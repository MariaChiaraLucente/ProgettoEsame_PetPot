package com.example.progettoesame_petpot.Calendar.Components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.remember
import androidx.navigation.NavController

data class Event(
    val quantity: Int,
    val date: String, // Giorno
    val month: Int, // Mese
    val time: String
)

@Composable

/*fun NewEventScreen(selectedDay: Int, events: MutableList<Event>, navController: NavController) {
    var quantity by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF8099C9))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("New Event for Day: $selectedDay", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                label = { Text("Time") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    events.add(Event(quantity.toIntOrNull() ?: 0, selectedDay.toString(), time))
                    navController.popBackStack() // Torna al calendario
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8))
            ) {
                Text("Save Event", color = Color.White)
            }
        }
    }
}


*/


fun NewEventScreen(selectedDay: Int, selectedMonth: Int, events: MutableList<Event>, navController: NavController) {
    var quantity by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF8099C9))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("New Event for Day: $selectedDay", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                label = { Text("Time") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    events.add(Event(quantity.toIntOrNull() ?: 0, selectedDay.toString(), selectedMonth, time))
                    navController.popBackStack() // Torna al calendario
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8))
            ) {
                Text("Save Event", color = Color.White)
            }
        }
    }
}