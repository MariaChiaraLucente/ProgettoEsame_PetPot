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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.progettoesame_petpot.model.Feed
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFeedScreen(viewModel: EditViewModel, userId: String, feedId: String, onBack: () -> Unit) {
    val feed by viewModel.feed.observeAsState()
    var showExitDialog by remember { mutableStateOf(false) }
    var showSaveDialog by remember { mutableStateOf(false) }

    LaunchedEffect(feedId) {
        viewModel.getFeedById(feedId)
    }

    var quantity by remember { mutableStateOf(0f) }
    var timeFix by remember { mutableStateOf("00:00") }
    var date by remember { mutableStateOf<Calendar?>(null) }
    var isDateValid by remember { mutableStateOf(true) }

    // Update values when the feed is loaded from the database
    LaunchedEffect(feed) {

        Log.d("DEBUG", "Feed ricevuto: $feed")
        feed?.let {
            quantity = it.quantity
            timeFix = it.timeFix
            date = Calendar.getInstance().apply { time = it.dateStart ?: Date() }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar", color = MaterialTheme.colorScheme.onBackground) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),

                navigationIcon = {
                    IconButton(onClick = { showExitDialog = true }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Date picker component
                    date?.let {
                        DatePickerComponent(
                            selectedDate = it,
                            onDateSelected = { newDate, isValid ->
                                date = newDate
                                isDateValid = isValid
                            })
                    }
                }
            }

            if (!isDateValid) {
                Text(
                    "⚠ Invalid date, please select a future date! ⚠",
                    color = Color.Yellow,
                    fontSize = 14.sp
                )
            }
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
                },
                feed
            )
            Log.d("DEBUG", "TimeFix: $timeFix")

            if (!isDateValid) {
                Text(
                    "⚠ Invalid time, please select a future time! ⚠",
                    color = Color.Yellow,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = {
                    if (isDateValid) {
                        showSaveDialog = true
                    }
                },
                enabled = isDateValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
            ) {
                Text("Save Changes", color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
    ConfirmExitDialog(
        showDialog = showExitDialog,
        onDismiss = { showExitDialog = false },
        onConfirm = {
            showExitDialog = false
            onBack()
        }
    )
    ConfirmSaveDialog(
        showDialog = showSaveDialog,
        onDismiss = { showSaveDialog = false },
        onConfirm = {
            showSaveDialog = false
            date?.let {
                viewModel.updateFeed(userId, feedId, quantity, timeFix, it.time, {
                    onBack()
                }, {
                    Log.e("UI", "Error updating feed")
                })
            }
        }
    )
}


@Composable
fun ConfirmSaveDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.secondary,
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Confirm Save", color = MaterialTheme.colorScheme.onBackground) },
            text = { Text(text = "Are you sure you want to save the changes?", color = MaterialTheme.colorScheme.onBackground) },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("Yes", color = MaterialTheme.colorScheme.onBackground)
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("No", color = MaterialTheme.colorScheme.onBackground)
                }
            }
        )
    }
}

@Composable
fun ConfirmExitDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.secondary,
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Confirm Exit", color = MaterialTheme.colorScheme.onBackground) },
            text = { Text(text = "Are you sure you want to leave this page?", color = MaterialTheme.colorScheme.onBackground) },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("Yes", color = MaterialTheme.colorScheme.onBackground)
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("No", color = MaterialTheme.colorScheme.onBackground)
                }
            }
        )
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
            modifier = Modifier
                .width(250.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            listOf("Giorno", "Mese", "Anno").forEach { label ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(label, color = MaterialTheme.colorScheme.onBackground, fontSize = 15.sp)
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
                    updateDate(
                        selectedDay,
                        selectedMonth,
                        selectedYear,
                        currentDate,
                        onDateSelected
                    )
                }
            )
            NumberPicker(
                value = selectedMonth,
                range = months.indices.toList(),
                displayValues = months,
                onValueChange = {
                    selectedMonth = it
                    updateDate(
                        selectedDay,
                        selectedMonth,
                        selectedYear,
                        currentDate,
                        onDateSelected
                    )
                }
            )
            NumberPicker(
                value = selectedYear,
                range = years,
                onValueChange = {
                    selectedYear = it
                    updateDate(
                        selectedDay,
                        selectedMonth,
                        selectedYear,
                        currentDate,
                        onDateSelected
                    )
                }
            )
        }
    }
}

// Funzione per aggiornare la data e verificarne la validità
private fun updateDate(
    day: Int,
    month: Int,
    year: Int,
    currentDate: Calendar,
    onDateSelected: (Calendar, Boolean) -> Unit
) {
    val updatedCalendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, day)
    }
    val isValidDate =
        updatedCalendar.get(Calendar.DAY_OF_MONTH) == day && updatedCalendar >= currentDate
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
                    color = MaterialTheme.colorScheme.onBackground,
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
    val quantities = (0..200).map { it * 0.5f }
    var selectedValue by remember { mutableStateOf(selectedQuantity) }

    LaunchedEffect(selectedQuantity) {
        selectedValue = selectedQuantity
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Quantity (kg)", color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        NumberPickerQuantity(
            value = selectedValue,
            range = quantities,
            displayValues = quantities.map { "%.1f".format(it) },
            onValueChange = {
                selectedValue = it
                onQuantitySelected(it)
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
    val index = range.indexOf(value).takeIf { it >= 0 } ?: 0
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = index)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(listState.isScrollInProgress) {
        // Se lo scroll non è in corso, aggiorniamo il valore
        if (!listState.isScrollInProgress) {
            val index = listState.firstVisibleItemIndex
            // Prendi il valore associato all'indice corrente
            val newValue = range.getOrNull(index) ?: value

            // Invia il nuovo valore solo se diverso dall'attuale
            if (newValue != value) {
                onValueChange(newValue)
            }
        }
    }

// Gestione del valore iniziale per la scrollata
    LaunchedEffect(value) {
        Log.d("DEBUG", "Value: $value")

        // Trova l'indice del valore iniziale e scorre alla posizione corretta solo quando il valore cambia
        val initialIndex = range.indexOf(value).takeIf { it >= 0 } ?: 0

        coroutineScope.launch {
            // Anima la scrollata all'indice iniziale in modo fluido
            listState.animateScrollToItem(initialIndex)
        }
    }


    Box(
        modifier = Modifier
            .height(120.dp)
            .width(100.dp)
            .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(8.dp))
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
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        // Indicatore centrale
        Box(
            modifier = Modifier
                .height(40.dp)
                .width(100.dp)
                .background(Color.Black.copy(alpha = 0.1f), shape = RoundedCornerShape(4.dp))
        )
    }
}

@Composable
fun TimePickerComponent(
    selectedTime: String, // Formato HH:MM
    onTimeSelected: (String) -> Unit,
    feed: Feed?
) {
    val isFeedLoaded = feed != null
    // Estrai ore e minuti dalla stringa selectedTime
    val (initialHour, initialMinute) = selectedTime.split(":").let {
        it[0].toInt() to it[1].toInt()
    }
    Log.d("DEBUG", "Initial Hour: $initialHour, Initial Minute: $initialMinute")

    var selectedHour by remember { mutableStateOf(initialHour) }
    var selectedMinute by remember { mutableStateOf(initialMinute) }

    // Aggiorna il tempo selezionato quando cambiano ore o minuti
    LaunchedEffect(selectedHour, selectedMinute) {
        val newTime = String.format("%02d:%02d", selectedHour, selectedMinute)
        onTimeSelected(newTime)
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            "Select Time",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        if (isFeedLoaded) {
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
                    },
                    feed
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Picker per i minuti (0-59)
                NumberPickerOrario(
                    value = selectedMinute,
                    range = (0..59).toList(),
                    displayValues = (0..59).map { "%02d".format(it) },
                    onValueChange = { newMinute ->
                        selectedMinute = newMinute
                    },
                    feed
                )
            }
        }
    }
}

@Composable
fun NumberPickerOrario(
    value: Int,
    range: List<Int>,
    displayValues: List<String>? = null,
    onValueChange: (Int) -> Unit,
    feed: Feed? = null
) {
    val index = range.indexOf(value).takeIf { it >= 0 } ?: 0
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = index)
    val coroutineScope = rememberCoroutineScope()

    // Aggiorna lo stato quando il valore cambia
    LaunchedEffect(value) {
        val initialIndex = range.indexOf(value).takeIf { it >= 0 } ?: 0
        coroutineScope.launch {
            listState.animateScrollToItem(initialIndex)
        }
    }

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val index = listState.firstVisibleItemIndex
            val newValue = range.getOrNull(index) ?: value
            if (newValue != value) {
                onValueChange(newValue)
            }
        }
    }

    Box(
        modifier = Modifier
            .height(80.dp)
            .width(150.dp)
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
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        // Indicatore centrale
        Box(
            modifier = Modifier
                .height(40.dp)
                .width(300.dp)
                .background(Color(0xFF5576B4).copy(alpha = 0.3f), shape = RoundedCornerShape(4.dp))
        )
    }
}

