package com.example.progettoesame_petpot.Calendar.Components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class EventViewModel : ViewModel() {
    var selectedDay by mutableStateOf<Int?>(null)
    var isEventScreenVisible by mutableStateOf(false)

    // Funzione per mostrare la schermata dell'evento
    fun showEventScreen(day: Int?) {
        selectedDay = day
        isEventScreenVisible = true

    }

    // Funzione per nascondere la schermata dell'evento
    fun hideEventScreen() {
        isEventScreenVisible = false
    }
}
