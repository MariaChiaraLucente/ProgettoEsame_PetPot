package com.example.progettoesame_petpot.Calendar.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.progettoesame_petpot.model.Feed

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
    val errorMessage by viewModel.errorCreationFeed.observeAsState()



    var showDialog by remember { mutableStateOf(false) }
    var selectedHour by remember { mutableStateOf(12) }
    var selectedMinute by remember { mutableStateOf(0) }
    var selectedQuantity by remember { mutableStateOf(100f) }
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
            .background(Color(0xFF5576B4))
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Next Feed", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1F2B85)
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),

                verticalArrangement = Arrangement.spacedBy(16.dp),
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
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            val (startDate, endDate) = viewModel.getStartAndEndDate()

                            val startDateFormatted =
                                startDate?.let { dateFormat.format(it) } ?: "Non selezionato"
                            val endDateFormatted = if (endDate != null) {
                                dateFormat.format(endDate)
                            } else {
                                startDateFormatted // Se endDate è null, mostra startDate
                            }


                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.White)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "$startDateFormatted",
                                    fontSize = 16.sp,
                                    color = Color(0xFF2F34BE),
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.White)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "$endDateFormatted",
                                    fontSize = 16.sp,
                                    color = Color(0xFF2F34BE),
                                    fontWeight = FontWeight.Bold
                                )
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

                                    .padding(horizontal = 16.dp),

                                elevation = CardDefaults.cardElevation(8.dp),

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

                                                tint = Color(0xFF2F34BE)

                                            )

                                        }


                                        Text(
                                            text = "$formattedDate",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF2F34BE)
                                        )

                                        IconButton(onClick = {

                                            viewModel.changeMonth(true)

                                        }) {

                                            Icon(

                                                imageVector = Icons.Default.ArrowForward,

                                                contentDescription = "Mese successivo",

                                                tint = Color(0xFF2F34BE)

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
                                            containerColor = Color(
                                                0xFF1F2B85
                                            )
                                        ),
                                        modifier = Modifier.padding(top = 16.dp)
                                    ) {
                                        Text("Conferma", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    // Resto del codice per l'orario e la quantità
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Orario: ${viewModel.feedOrarioFisso.value}",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 24.sp,
                            color = Color(0xFF2F34BE),
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Card(
                                modifier = Modifier
                                    .width(280.dp)
                                    // Imposta una larghezza maggiore per contenere entrambe le colonne
                                    .height(210.dp), // Imposta l'altezza per la card
                                elevation = CardDefaults.cardElevation(8.dp),

                                ) {
                                // Row per affiancare le due LazyColumn
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,  // Spazio equo tra le due colonne
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Colonna per le ore
                                    LazyColumn(
                                        modifier = Modifier
                                            .width(130.dp)  // Imposta larghezza per la colonna delle ore
                                            .fillMaxHeight()
                                            .background(Color(0xFFA2B0CA)),
                                    ) {
                                        items(24) { index1 ->
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        selectedHour = index1
                                                        viewModel.updateFeedOrarioFisso(
                                                            "%02d:%02d".format(
                                                                selectedHour,
                                                                selectedMinute
                                                            )
                                                        )
                                                    }
                                                    .background(
                                                        color = if ("%02d".format(index1) == "%02d".format(
                                                                selectedHour
                                                            )
                                                        ) Color(0xFF7F96C1) else Color.Transparent
                                                    ),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = "${"%02d".format(index1)}",
                                                    modifier = Modifier.padding(16.dp),
                                                    fontSize = 24.sp,
                                                    color = Color(0xFF2F34BE),
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }

                                    // Colonna per i minuti
                                    LazyColumn(
                                        modifier = Modifier
                                            .width(130.dp)  // Imposta larghezza per la colonna dei minuti
                                            .fillMaxHeight()
                                            .background(Color(0xFFA2B0CA)),
                                    ) {
                                        items(60) { index ->
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        selectedMinute = index
                                                        viewModel.updateFeedOrarioFisso(
                                                            "%02d:%02d".format(
                                                                selectedHour,
                                                                selectedMinute
                                                            )
                                                        )
                                                    }
                                                    .background(
                                                        color = if ("%02d".format(index) == "%02d".format(
                                                                selectedMinute
                                                            )
                                                        ) Color(0xFF7F96C1) else Color.Transparent
                                                    ),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = "${"%02d".format(index)}",
                                                    modifier = Modifier.padding(16.dp),
                                                    fontSize = 24.sp,
                                                    color = Color(0xFF2F34BE),
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Grammi a pasto: ${viewModel.feedQuantita.value}g",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 24.sp,
                        color = Color(0xFF2F34BE),
                        fontWeight = FontWeight.Bold
                    )
                    Card(
                        modifier = Modifier
                            .width(280.dp)  // Imposta una larghezza per la Card
                            .height(210.dp),  // Imposta l'altezza per la Card
                        elevation = CardDefaults.cardElevation(8.dp),
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFFA2B0CA)),  // Sfondo per la LazyColumn
                        ) {
                            items((0..500 step 50).toList()) { quantity ->  // Crea una lista da 0 a 500 con step 50
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedQuantity = quantity.toFloat()
                                            viewModel.updateFeedQuantita(selectedQuantity)
                                        }
                                        .background(
                                            color = if (selectedQuantity == quantity.toFloat()) Color(
                                                0xFF7F96C1
                                            ) else Color.Transparent
                                        ),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "${quantity}g",
                                        modifier = Modifier.padding(16.dp),
                                        fontSize = 24.sp,
                                        color = Color(0xFF2F34BE),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                }

                item {


                    if (errorMessage != null) {
                        Text(
                            text = errorMessage!!,
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Button(
                        onClick = {
                            showDialog = true
                        },
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F2B85)),
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Feed",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Confirm Save") },
            text = { Text(text = "Are you sure you want to save this feed?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.validateAndSaveFeed()
                        viewModel.completeSelection()
                        showDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
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

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(330.dp)
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF5576B4))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            // Row for day names
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                dayNames.forEach { dayName ->
                    Text(
                        text = dayName,
                        color = Color.White,
                        fontSize = 16.sp,
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
                            val backgroundColor = when {
                                isSelected -> Color(0xFF1F2B85) // Selected color

                                else -> Color.Blue.copy(alpha = 0.3f)
                            }
                            Column {
                                Box(
                                    modifier = Modifier
                                        .size(35.dp)
                                        .background(backgroundColor, CircleShape)
                                        .clickable { onDayClick(currentDay) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = currentDay.toString(),
                                        color = Color.White,
                                        fontSize = 16.sp
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
