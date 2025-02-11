package com.example.progettoesame_petpot.Calendar.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.progettoesame_petpot.model.Feed

// In questa schermata di modifica, ricevi un feed da modificare come parametro
@Composable
fun EditFeedScreen(viewModel: EventViewModel, feedToEdit: Feed) {
    // Precompiliamo i campi con i dati esistenti
    val selectedStartDate = feedToEdit.dateStart
    val selectedEndDate = feedToEdit.dateEnd
    val timeFix = feedToEdit.timeFix
    val quantity = feedToEdit.quantity

    // Creiamo variabili di stato per modificare i valori
    val feedOrarioFisso = remember { mutableStateOf(timeFix) }
    val feedQuantita = remember { mutableStateOf(quantity) }

    // UI per modificare i feed
    Column {
        // Data Picker
        // Potresti usare un DatePicker per selezionare la data
        DatePicker(selectedStartDate, selectedEndDate, onDateSelected = { start, end ->
            viewModel._selectedStartDate.value = start
            viewModel._selectedEndDate.value = end
        })

        // Orario fisso
        TextField(value = feedOrarioFisso.value, onValueChange = { feedOrarioFisso.value = it })

        // Quantità
        TextField(value = feedQuantita.value.toString(), onValueChange = { feedQuantita.value = it.toFloat() })

        Button(onClick = {
            // Salviamo i cambiamenti
            viewModel.saveModifiedFeed(feedToEdit.copy(
                timeFix = feedOrarioFisso.value,
                dateStart = selectedStartDate,
                dateEnd = selectedEndDate,
                quantity = feedQuantita.value
            ))
        }) {
            Text("Salva modifiche")
        }
    }
}
