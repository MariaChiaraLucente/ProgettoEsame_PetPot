package com.example.progettoesame_petpot.Calendar.Components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter


data class Feed(
    val id: String? = null,
    val titolo: String = "",
    val descrizione: String = "",
    val orarioFisso: String = "",
    val dataFine: String = "",
    val dataInizio: String = "",
    val quantità: Float = 0f
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "titolo" to titolo,
            "descrizione" to descrizione,
            "orarioFisso" to orarioFisso,
            "dataFine" to dataFine,
            "dataInizio" to dataInizio,
            "grammi" to quantità
        )
    }
}


class CalendarViewModel : ViewModel() {

    // Firebase Database Reference
    private val database = FirebaseDatabase.getInstance().getReference("feeds")

    private val _selectedStartDay = MutableStateFlow<Int?>(null)
    val selectedStartDay: StateFlow<Int?> get() = _selectedStartDay

    private val _selectedEndDay = MutableStateFlow<Int?>(null)
    val selectedEndDay: StateFlow<Int?> get() = _selectedEndDay


    var currentMonth = mutableStateOf(0) // Gennaio come mese iniziale


    // Funzione per aggiornare il giorno selezionato
    fun selectDay(day: Int) {
        if (_selectedStartDay.value == null) {

            _selectedStartDay.value = day

        } else if (_selectedEndDay.value == null) {

            if (day >= _selectedStartDay.value!!) {

                _selectedEndDay.value = day

            } else {

                // Se il giorno selezionato è prima del giorno di inizio, aggiorna il giorno di inizio

                _selectedStartDay.value = day

            }

        } else {

            // Resetta la selezione se entrambi i giorni sono già selezionati

            _selectedStartDay.value = day

            _selectedEndDay.value = null

        }

    }

    // Stati per i dati del feed
    var feedTitle = mutableStateOf("")
        private set
    var feedDescription = mutableStateOf("")
        private set
    var feedOrarioFisso = mutableStateOf("12:00") // Default: 12:00
        private set
    var feedQuantita = mutableStateOf(0f)
        private set


    // Metodo per aggiornare il mese corrente
    fun changeMonth(offset: Int) {
        currentMonth.value = (currentMonth.value + offset).coerceIn(0, 11)
    }

    // Metodi per aggiornare gli attributi del feed
    fun updateFeedTitle(title: String) {
        feedTitle.value = title
    }

    fun updateFeedDescription(description: String) {
        feedDescription.value = description
    }

    fun updateFeedOrarioFisso(orario: String) {
        feedOrarioFisso.value = orario
    }

    fun updateFeedQuantita(quantita: Float) {
        feedQuantita.value = quantita
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createFeedInDatabase(
        startDay: Int?,
        endDay: Int?,
        startMonth : Int?,
        endMonth: Int?,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        if (startDay == null || endDay == null) {
            onError(IllegalArgumentException("Start day or end day is not selected"))
            return
        }

        val currentYear = 2025
        val month = currentMonth.value + 1
        val startDate = LocalDate.of(currentYear, month, startDay)
        val endDate = LocalDate.of(currentYear, month, endDay)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val feed = Feed(
            id = database.push().key,
            titolo = feedTitle.value,
            descrizione = feedDescription.value,
            orarioFisso = feedOrarioFisso.value,
            dataInizio = startDate.format(formatter),
            dataFine = endDate.format(formatter),
            quantità = feedQuantita.value
        )

        feed.id?.let { id ->
            database.child(id).setValue(feed.toMap())
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onError(it) }
        } ?: run {
            onError(IllegalArgumentException("Feed ID non generato"))
        }
    }
}





