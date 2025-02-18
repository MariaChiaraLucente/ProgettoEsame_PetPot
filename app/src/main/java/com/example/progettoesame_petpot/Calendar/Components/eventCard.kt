package com.example.progettoesame_petpot.Calendar.Components

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.progettoesame_petpot.viewmodel.CalendarViewModel


import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun FeedCreationScreen(
    navController: NavController,
    viewModel: EventViewModel,


    ) {
    var showSaveDialog by remember { mutableStateOf(false) }
    val errorMessage = viewModel.errorMessage
    val showAlertDialog = viewModel.showAlertDialog


    // Inside your composable function
    val context = LocalContext.current
    val feedToEdit = viewModel.selectedFeed
    // Se il feed non è nullo, carica i dati nel ViewModel
    if (feedToEdit != null) {
        viewModel.setFeed(feedToEdit)
    }

    var selectedHour by remember { mutableStateOf(12) }
    var selectedMinute by remember { mutableStateOf(0) }
    var selectedQuantity by remember { mutableStateOf(0f) }
    var isCalendarExpanded by remember { mutableStateOf(false) }

    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val currentDate = Calendar.getInstance().apply {
        set(Calendar.MONTH, viewModel.currentMonth)
        set(Calendar.YEAR, viewModel.currentYear)
    }.time
    val formattedDate = dateFormat.format(currentDate)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Event", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),

                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
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
            //Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),

                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                item {
                    // Card cliccabile per selezionare i giorni
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                isCalendarExpanded = !isCalendarExpanded
                            }, // Espandi o chiudi il calendario
                        elevation = CardDefaults.cardElevation(8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary, contentColor = MaterialTheme.colorScheme.onBackground)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            val (startDate, endDate) = viewModel.getStartAndEndDate()

                            val startDateFormatted =
                                startDate?.let { dateFormat.format(it) } ?: "Not selected"
                            val endDateFormatted = if (endDate != null) {
                                dateFormat.format(endDate)
                            } else {
                                startDateFormatted // Se endDate è null, mostra startDate
                            }
                            Column {
                                Text(
                                    text = "Start Date",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.Start)
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF6C87BB))
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "$startDateFormatted",
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Column {
                                Text(
                                    text = "End Date",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.Start)
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF6C87BB))
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "$endDateFormatted",
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        // Animazione smooth per la CalendarGrid
                        AnimatedVisibility(
                            visible = isCalendarExpanded,
                            enter = androidx.compose.animation.expandVertically(
                                animationSpec = tween(durationMillis = 300)
                            ),
                            exit = androidx.compose.animation.shrinkVertically(
                                animationSpec = tween(durationMillis = 300)
                            )

                        ) {

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                elevation = CardDefaults.cardElevation(8.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background, contentColor = MaterialTheme.colorScheme.onBackground),
                                shape = RoundedCornerShape(16.dp)

                            ) {

                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally

                                ) {

                                    // Selezione del mese
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        IconButton(onClick = {
                                            viewModel.changeMonth(false)
                                        }) {

                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = "Mese precedente",
                                                tint = MaterialTheme.colorScheme.onBackground

                                            )

                                        }


                                        Text(
                                            text = "$formattedDate",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )

                                        IconButton(onClick = {

                                            viewModel.changeMonth(true)

                                        }) {

                                            Icon(
                                                imageVector = Icons.Default.ArrowForward,
                                                contentDescription = "Mese successivo",
                                                tint = MaterialTheme.colorScheme.onBackground
                                            )
                                        }
                                    }


                                    // CalendarGrid composable

                                    CalendarGridFeed(
                                        calendarViewModel = viewModel,
                                        daysInMonth = viewModel.getDaysInCurrentMonth(),
                                        currentMonth = viewModel.currentMonth,
                                        currentYear = viewModel.currentYear,
                                        selectedStartDate = viewModel.selectedStartDate,
                                        selectedEndDate = viewModel.selectedEndDate,
                                        onDayClick = { day ->
                                            viewModel.selectDay(day)
                                        }
                                    )

                                    // Bottone per confermare e chiudere il calendario
                                    Button(
                                        onClick = {
                                            isCalendarExpanded = false
                                            viewModel.completeSelection()
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.surface
                                        ),
                                        modifier = Modifier.padding(top = 16.dp)
                                    ) {
                                        Text("Confirm", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Time: ${viewModel.feedOrarioFisso.value}",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )

                        // 🔹 Sostituzione della Card con TimePickerComponent
                        TimePickerComponent(
                            selectedHour = selectedHour,
                            selectedMinute = selectedMinute,
                            onTimeSelected = { hour, minute ->
                                selectedHour = hour
                                selectedMinute = minute
                                viewModel.updateFeedOrarioFisso("%02d:%02d".format(hour, minute))
                            }
                        )

                    }
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Quantity: ${viewModel.feedQuantita.value}g",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                    QuantityPicker(
                        selectedQuantity = selectedQuantity,
                        onQuantitySelected = { quantity ->
                            selectedQuantity = quantity
                            viewModel.updateFeedQuantita(quantity) // Chiamata al ViewModel come nella vecchia struttura
                        }
                    )

                }

                item {

                    // Pulsante "Save Feed"
                    Button(
                        onClick = {
                            if (viewModel.validateAndSaveFeed()) {
                                // Se non ci sono errori, l'AlertDialog verrà mostrato
                            }
                        },
                        enabled = errorMessage == null, // Disabilita il pulsante se c'è un errore
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .height(50.dp)
                            .width(200.dp)
                            .padding(horizontal = 16.dp)
                            .border(2.dp, Color(0xFF0A0A0A), shape = RoundedCornerShape(16.dp))
                    ) {
                        Text(
                            text = "Save Feed",
                            fontSize = 17.sp, fontWeight = FontWeight.Bold
                        )
                    }

                    // Mostra l'AlertDialog solo se non ci sono errori
                    if (showAlertDialog) {
                        AlertDialog(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            onDismissRequest = {
                                viewModel.hideDialog() // Nascondi l'AlertDialog
                            },
                            title = {
                                Text(text = "Success", color = MaterialTheme.colorScheme.onBackground)
                            },
                            text = {
                                Text(text = "Feed saved successfully!", color = MaterialTheme.colorScheme.onBackground)
                            },
                            confirmButton = {
                                Button(
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
                                    onClick = {
                                        viewModel.hideDialog() // Nascondi l'AlertDialog
                                        navController.navigate("Calendar")
                                    }
                                ) {
                                    Text("OK", color = MaterialTheme.colorScheme.onBackground)
                                }
                            }
                        )
                    }
                    if (viewModel.showErrorDialog) {
                        AlertDialog(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            onDismissRequest = { viewModel.hideError() },
                            title = { Text("Attention!") },
                            text = { Text(viewModel.errorMessage ?: "Selezione non valida, riprova.") },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        viewModel.resetFeedSelection()
                                    },
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
                                    ) {
                                    Text("Try again", color = MaterialTheme.colorScheme.onBackground)
                                }
                            },
                        )
                    }

                }
            }
        }
    }

    ConfirmSave(
        showDialog = showSaveDialog,
        onDismiss = { showSaveDialog = false },
        onConfirm = {
            showSaveDialog = false
            viewModel.saveFeed()
            viewModel.completeSelection()
            Toast.makeText(context, "Feed created successfully!", Toast.LENGTH_SHORT).show()
            navController.navigate("Drawers")
        }
    )
}

@Composable
fun ConfirmSave(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Confirm Save") },
            text = { Text(text = "Are you sure you want to save this feed?") },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("No")
                }
            }
        )
    }
}


@Composable
fun CalendarGridFeed(
    calendarViewModel: EventViewModel,
    daysInMonth: Int,
    currentMonth: Int,
    currentYear: Int,
    selectedStartDate: Date?,
    selectedEndDate: Date?,
    onDayClick: (Int) -> Unit
) {
    val calendar = Calendar.getInstance()
    val dayNames = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val todayCalendar = Calendar.getInstance()

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(310.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(5.dp)
        ) {

            // Row for day names
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                dayNames.forEach { dayName ->
                    Text(
                        text = dayName,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Rows for days in the month
            for (week in 0 until 6) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (day in 1..7) {
                        val currentDay = week * 7 + day
                        if (currentDay <= daysInMonth) {
                            // Create the date corresponding to the current day
                            calendar.set(Calendar.YEAR, currentYear)
                            calendar.set(Calendar.MONTH, currentMonth)
                            calendar.set(Calendar.DAY_OF_MONTH, currentDay)
                            val currentDate = calendar.time

                            // Check if the day is selected or in the range
                            val isSelected = calendarViewModel.isDateSelected(
                                currentDate,
                                selectedStartDate,
                                selectedEndDate
                            )
                            val hasFeed = calendarViewModel.isFeedDay(currentDate)
                            val isPastDate = currentDate.before(todayCalendar.time)
                            val isToday = calendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR) &&
                                    calendar.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH) &&
                                    calendar.get(Calendar.DAY_OF_MONTH) == todayCalendar.get(Calendar.DAY_OF_MONTH)
                            val backgroundColor = when {
                                isSelected -> Color(0xFF1F2B85) // Selected color

                                else -> MaterialTheme.colorScheme.background
                            }
                            Column {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(
                                            when {
                                                isToday -> Color(0xFF2E3957)
                                                isPastDate -> Color(0xFF8791A2)
                                                isSelected -> backgroundColor
                                                else -> MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
                                            },
                                    CircleShape
                                )
                                        .clickable { onDayClick(currentDay) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = currentDay.toString(),
                                        color = when{
                                            isToday -> Color.White
                                            isPastDate -> Color(0xFFC2C0C0)
                                            else -> Color.White.copy(alpha = 0.7f)
                                        },
                                        fontSize = 14.sp
                                    )
                                }
                                if (hasFeed) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(Color.Gray, CircleShape)
                                            .align(Alignment.CenterHorizontally)
                                    )
                                }
                            }

                        } else {
                            Box(
                                modifier = Modifier
                                    .size(35.dp)
                                    .background(Color.Transparent, CircleShape)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun TimePickerComponent(
    selectedHour: Int,
    selectedMinute: Int,
    onTimeSelected: (Int, Int) -> Unit
) {
    val hours = (0..23).toList()
    val minutes = (0..59).toList()

    var currentHour by remember { mutableStateOf(selectedHour) }
    var currentMinute by remember { mutableStateOf(selectedMinute) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header

            // Scrollable pickers per ore e minuti
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Picker per le ore (0-23)
                NumberPickerOrario(
                    value = currentHour,
                    range = hours,
                    displayValues = hours.map { "%02d".format(it) },
                    onValueChange = { newHour ->
                        currentHour = newHour
                        onTimeSelected(currentHour, currentMinute)
                    }
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Picker per i minuti (0-59)
                NumberPickerOrario(
                    value = currentMinute,
                    range = minutes,
                    displayValues = minutes.map { "%02d".format(it) },
                    onValueChange = { newMinute ->
                        currentMinute = newMinute
                        onTimeSelected(currentHour, currentMinute)
                    }
                )
            }
        }
    }
}

@Composable
fun QuantityPicker(
    selectedQuantity: Float,
    onQuantitySelected: (Float) -> Unit
) {
    val quantities = (10..55 step 5).toList().map { it.toFloat() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(12.dp)),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Spacer(modifier = Modifier.height(8.dp))

        NumberPickerQuantity(
            value = selectedQuantity,
            range = quantities,
            displayValues = quantities.map { "%.1f".format(it) },
            onValueChange = { newValue ->
                onQuantitySelected(newValue) // Chiamata alla funzione passata come parametro
            }
        )
    }
}





