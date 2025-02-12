package com.example.progettoesame_petpot.Calendar.Components


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date



@Composable
fun EditFeedScreen(viewModel: EditViewModel, userId: String, feedId: String, onBack: () -> Unit) {
    val feed by viewModel.feed.observeAsState()

    LaunchedEffect(feedId) {
        viewModel.getFeedById(feedId)
    }

    var quantity by remember { mutableStateOf(0f) }
    var timeFix by remember { mutableStateOf("00:00") }
    var date by remember { mutableStateOf<Calendar?>(null) }
    var isDateValid by remember { mutableStateOf(true) }

    // Update values when the feed is loaded from the database
    LaunchedEffect(feed) {
        feed?.let {
            quantity = it.quantity
            timeFix = it.timeFix
            date = Calendar.getInstance().apply { time = it.dateStart ?: Date() } // Imposta la data salvata
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Edit Feed", style = MaterialTheme.typography.bodyLarge)

        // Usa il QuantityPickerComponent invece del TextField
        QuantityPickerComponent(
            selectedQuantity = quantity,
            onQuantitySelected = { newQuantity ->
                quantity = newQuantity
            }
        )

        // Time Picker
        TimePickerComponent(
            selectedTime = timeFix,
            onTimeSelected = { newTime ->
                timeFix = newTime
            }
        )

        // Date picker component
        date?.let {
            DatePickerComponent(selectedDate = it, onDateSelected = { newDate, isValid ->
                date = newDate
                isDateValid = isValid
            })
        }

        if (!isDateValid) {
            Text("Invalid date, please select a future date!", color = Color.Red, fontSize = 14.sp)
        }

        Button(
            onClick = {
                if (isDateValid) {
                    date?.let {
                        viewModel.updateFeed(userId, feedId, quantity, timeFix, it.time, {
                            onBack()
                        }, {
                            Log.e("UI", "Error updating feed")
                        })
                    }
                }
            },
            enabled = isDateValid
        ) {
            Text("Save Changes")
        }
    }
}

@Composable
fun DatePickerComponent(
    selectedDate: Calendar,
    onDateSelected: (Calendar, Boolean) -> Unit
) {
    val days = (1..31).toList()
    val months = listOf(
        "Gen", "Feb", "Mar", "Apr", "Mag", "Giu",
        "Lug", "Ago", "Set", "Ott", "Nov", "Dic"
    )
    val years = (2025..2035).toList()

    var selectedDay by remember { mutableStateOf(selectedDate.get(Calendar.DAY_OF_MONTH)) }
    var selectedMonth by remember { mutableStateOf(selectedDate.get(Calendar.MONTH)) }
    var selectedYear by remember { mutableStateOf(selectedDate.get(Calendar.YEAR)) }


    LaunchedEffect(selectedDate) {
        selectedDay = selectedDate.get(Calendar.DAY_OF_MONTH)
        selectedMonth = selectedDate.get(Calendar.MONTH)
        selectedYear = selectedDate.get(Calendar.YEAR)
    }

    val currentDate = Calendar.getInstance()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.width(250.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("Giorno", "Mese", "Anno").forEach { label ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(label, color = Color.Black.copy(alpha = 1f), fontSize = 15.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Scrollable pickers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberPicker(
                value = selectedDay,
                range = days,
                onValueChange = {
                    selectedDay = it
                    updateDate(selectedDay, selectedMonth, selectedYear, currentDate, onDateSelected)
                }
            )
            NumberPicker(
                value = selectedMonth,
                range = months.indices.toList(),
                displayValues = months,
                onValueChange = {
                    selectedMonth = it
                    updateDate(selectedDay, selectedMonth, selectedYear, currentDate, onDateSelected)
                }
            )
            NumberPicker(
                value = selectedYear,
                range = years,
                onValueChange = {
                    selectedYear = it
                    updateDate(selectedDay, selectedMonth, selectedYear, currentDate, onDateSelected)
                }
            )
        }
    }
}

// Funzione per aggiornare la data e verificarne la validità
private fun updateDate(day: Int, month: Int, year: Int, currentDate: Calendar, onDateSelected: (Calendar, Boolean) -> Unit) {
    val updatedCalendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, day)
    }
    val isValidDate = updatedCalendar.get(Calendar.DAY_OF_MONTH) == day && updatedCalendar >= currentDate
    onDateSelected(updatedCalendar, isValidDate)
}

@Composable
fun NumberPicker(
    value: Int,
    range: List<Int>,
    displayValues: List<String>? = null,
    onValueChange: (Int) -> Unit,
    onOverflow: (() -> Unit)? = null
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = range.indexOf(value))
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val index = listState.firstVisibleItemIndex
            val newValue = range[index % range.size] // Rotazione infinita
            onValueChange(newValue)

            coroutineScope.launch {
                listState.animateScrollToItem(index)
            }

            if (newValue == range.last() && onOverflow != null) {
                onOverflow()
            }
        }
    }

    Box(
        modifier = Modifier
            .height(80.dp)
            .width(80.dp)
    ) {
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(range + range + range) { index, number -> // Rotazione infinita
                val realIndex = index % range.size
                val isSelected = realIndex == listState.firstVisibleItemIndex % range.size
                val textSize = if (isSelected) 26.sp else 20.sp
                val textColor = if (isSelected) Color.Black else Color.Black.copy(alpha = 0.5f)

                Text(
                    text = displayValues?.get(realIndex) ?: number.toString().padStart(2, '0'),
                    fontSize = textSize,
                    color = textColor,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        // Indicatore centrale
        Box(
            modifier = Modifier
                .height(40.dp)
                .width(75.dp)
                .background(Color.Black.copy(alpha = 0.3f), shape = RoundedCornerShape(4.dp))
        )
    }
}

@Composable
fun QuantityPickerComponent(
    selectedQuantity: Float,
    onQuantitySelected: (Float) -> Unit
) {
    // Definiamo un intervallo di valori per la quantità (es. da 0.0 a 100.0 con incrementi di 0.5)
    val quantities = (0..200).map { it * 0.5f } // Genera [0.0, 0.5, 1.0, 1.5, ..., 100.0]

    var selectedValue by remember { mutableStateOf(selectedQuantity) }

    LaunchedEffect(selectedQuantity) {
        selectedValue = selectedQuantity
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            "Quantità (kg)",
            color = Color.Black.copy(alpha = 0.5f),
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Scrollable picker
        NumberPickerQuantity(
            value = selectedValue,
            range = quantities,
            displayValues = quantities.map { "%.1f".format(it) },
            onValueChange = { newValue ->
                selectedValue = newValue
                onQuantitySelected(newValue)
            }
        )
    }
}

@Composable
fun NumberPickerQuantity(
    value: Float,
    range: List<Float>,
    displayValues: List<String>? = null,
    onValueChange: (Float) -> Unit
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = range.indexOf(value))
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val index = listState.firstVisibleItemIndex
            val newValue = range.getOrNull(index) ?: value
            onValueChange(newValue)

            coroutineScope.launch {
                listState.animateScrollToItem(index)
            }
        }
    }

    Box(
        modifier = Modifier
            .height(80.dp)
            .width(100.dp)
    ) {
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(range) { index, number ->
                val isSelected = index == listState.firstVisibleItemIndex
                val textSize = if (isSelected) 26.sp else 20.sp
                val textColor = if (isSelected) Color.Black else Color.Black.copy(alpha = 0.5f)

                Text(
                    text = displayValues?.get(index) ?: "%.1f".format(number),
                    fontSize = textSize,
                    color = textColor,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        // Indicatore centrale
        Box(
            modifier = Modifier
                .height(40.dp)
                .width(90.dp)
                .background(Color.Black.copy(alpha = 0.3f), shape = RoundedCornerShape(4.dp))
        )
    }
}
@Composable
fun TimePickerComponent(
    selectedTime: String, // Formato HH:MM
    onTimeSelected: (String) -> Unit
) {
    // Estrai ore e minuti dalla stringa selectedTime
    val (initialHour, initialMinute) = selectedTime.split(":").let {
        it[0].toInt() to it[1].toInt()
    }

    var selectedHour by remember { mutableStateOf(initialHour) }
    var selectedMinute by remember { mutableStateOf(initialMinute) }

    // Aggiorna il tempo selezionato quando cambiano ore o minuti
    LaunchedEffect(selectedHour, selectedMinute) {
        val newTime = String.format("%02d:%02d", selectedHour, selectedMinute)
        onTimeSelected(newTime)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            "Seleziona l'ora",
            color = Color.Black.copy(alpha = 0.5f),
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Scrollable pickers per ore e minuti
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Picker per le ore (0-23)
            NumberPickerOrario(
                value = selectedHour,
                range = (0..23).toList(),
                displayValues = (0..23).map { "%02d".format(it) },
                onValueChange = { newHour ->
                    selectedHour = newHour
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Picker per i minuti (0-59)
            NumberPickerOrario(
                value = selectedMinute,
                range = (0..59).toList(),
                displayValues = (0..59).map { "%02d".format(it) },
                onValueChange = { newMinute ->
                    selectedMinute = newMinute
                }
            )
        }
    }
}

@Composable
fun NumberPickerOrario(
    value: Int,
    range: List<Int>,
    displayValues: List<String>? = null,
    onValueChange: (Int) -> Unit
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = range.indexOf(value))
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val index = listState.firstVisibleItemIndex
            val newValue = range.getOrNull(index) ?: value
            onValueChange(newValue)

            coroutineScope.launch {
                listState.animateScrollToItem(index)
            }
        }
    }

    Box(
        modifier = Modifier
            .height(80.dp)
            .width(100.dp)
    ) {
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(range) { index, number ->
                val isSelected = index == listState.firstVisibleItemIndex
                val textSize = if (isSelected) 26.sp else 20.sp
                val textColor = if (isSelected) Color.Black else Color.Black.copy(alpha = 0.5f)

                Text(
                    text = displayValues?.get(index) ?: "%02d".format(number),
                    fontSize = textSize,
                    color = textColor,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        // Indicatore centrale
        Box(
            modifier = Modifier
                .height(40.dp)
                .width(90.dp)
                .background(Color.Black.copy(alpha = 0.3f), shape = RoundedCornerShape(4.dp))
        )
    }
}

