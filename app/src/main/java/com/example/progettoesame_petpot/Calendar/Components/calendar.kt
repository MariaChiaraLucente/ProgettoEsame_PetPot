package com.example.progettoesame_petpot


/*
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalendarScreen() {
    var selectedDay by remember { mutableStateOf<Int?>(null) }
    var isSheetVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF5576B4))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Calendar",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                CalendarGrid { day ->
                    selectedDay = day
                    isSheetVisible = true // Quando un giorno viene cliccato, mostriamo la schermata di dettaglio
                }
            }
        }

        // Mostra il pulsante per creare un nuovo evento
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Handle new event click */ },
            colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8)),
            modifier = Modifier
                .width(180.dp)
                .height(45.dp),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text("New Event", color = Color.White, fontSize = 16.sp)
        }

        // Modifica 1: Animazione di swipe per visualizzare il contenuto (card vuote) quando il giorno è selezionato
        AnimatedVisibility(
            visible = isSheetVisible,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(), // Anima lo swipe dall'alto
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut() // Anima lo swipe verso l'alto quando la schermata viene nascosta
        ) {
            SwipeableScreenContent(day = selectedDay) // La schermata che appare con le card vuote
        }
    }
}

@Composable
fun CalendarGrid(onDayClick: (Int) -> Unit) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        for (i in 0 until 5) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (i == 4) Arrangement.Start else Arrangement.SpaceEvenly
            ) {
                for (j in 1..7) {
                    val day = i * 7 + j
                    if (day <= 31) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.Blue.copy(alpha = 0.3f), CircleShape)
                                .clickable { onDayClick(day) }, // Gestiamo il click per aprire la schermata con le card
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = day.toString(), color = Color.White, fontSize = 16.sp)
                        }
                        if (j < 7 && day < 31) {
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SwipeableScreenContent(day: Int?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selected Day: ${day ?: "None"}",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Aggiunta delle card vuote che vengono visualizzate
        for (i in 1..3) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
            ) {
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Card $i", color = Color.Gray)
                }
            }
        }
    }
}
*/

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.example.progettoesame_petpot.Calendar.Components.Event
import com.example.progettoesame_petpot.Calendar.Components.EventViewModel
import com.example.progettoesame_petpot.Calendar.Components.NewEventScreen


//aggiornamento 09/12: l interfaccia funziona ma non è un vero calendario
/*@Composable
fun CalendarScreen() {
    var selectedDay by remember { mutableStateOf<Int?>(null) }
    var isSheetVisible by remember { mutableStateOf(false) }
    var isFullScreen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Calendar",
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp, top = 26.dp)
            )
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF5576B4))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CalendarGrid { day ->
                        if (selectedDay == day && isSheetVisible) {
                            isSheetVisible = false // Chiude temporaneamente
                            selectedDay = null // Resetta il giorno selezionato
                        } else {
                            selectedDay = day
                            isSheetVisible = true // Apre il pannello
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Handle new event click */ },
                colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8)),
                modifier = Modifier
                    .width(180.dp)
                    .height(45.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text("New Event", color = Color.White, fontSize = 16.sp)
            }
        }

        AnimatedVisibility(
            visible = isSheetVisible,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {

            SwipeableScreenContent(
                day = selectedDay,
                isFullScreen = isFullScreen,
                onFullScreenToggle = {
                    isFullScreen = !isFullScreen
                },
                onClose = {
                    isSheetVisible = false
                }
            )
        }
    }
}

@Composable
fun CalendarGrid(onDayClick: (Int) -> Unit) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        for (i in 0 until 5) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (i == 4) Arrangement.Start else Arrangement.SpaceEvenly
            ) {
                for (j in 1..7) {
                    val day = i * 7 + j
                    if (day <= 31) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.Blue.copy(alpha = 0.3f), CircleShape)
                                .clickable { onDayClick(day) }, // Gestiamo il click per aprire la schermata con le card
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = day.toString(), color = Color.White, fontSize = 16.sp)
                        }
                        if (j < 7 && day < 31) {
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}



@Composable
fun SwipeableScreenContent(day: Int?, isFullScreen: Boolean, onFullScreenToggle: () -> Unit, onClose: () -> Unit) {
    var offsetY by remember { mutableStateOf(0f) }
    var height by remember { mutableStateOf(300.dp) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    offsetY += delta
                    height = (600.dp - offsetY.dp).coerceAtLeast(3.dp).coerceAtMost(900.dp)
                    if (height < 5.dp) {
                        onClose()
                    }
                }
            )
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selected Day: ${day ?: "None"}",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(10) { index ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
                ) {
                    Box(
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Card ${index + 1}", color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onFullScreenToggle,
            colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8)),
            modifier = Modifier
                .width(180.dp)
                .height(45.dp),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text(if (isFullScreen) "Exit Full Screen" else "Go Full Screen", color = Color.White, fontSize = 16.sp)
        }
    }
}

 */


@Composable
fun CalendarScreen( navController: NavController, events: MutableList<Event>) {
    // Stato del mese corrente (gennaio, febbraio, marzo)
    var currentMonth by remember { mutableStateOf(1) } // 0 = gennaio, 1 = febbraio, 2 = marzo
    var selectedDay by remember { mutableStateOf<Int?>(19) }
    var isSheetVisible by remember { mutableStateOf(true) }
    var isFullScreen by remember { mutableStateOf(false) }


    val monthNames = listOf("January", "February", "March")
    val daysInMonths = listOf(31, 28, 31) // Giorni per ciascun mese (senza considerare anni bisestili)



    Box(
        modifier = Modifier
            .fillMaxSize()


    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF8099C9)),

            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "Calendar",
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp, top = 26.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        currentMonth = (currentMonth - 1).coerceAtLeast(0) // Indietro di un mese
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5576B4).copy(alpha = 0.5f)),

                ) {
                    Icon(
                            imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Previous Month",
                    tint = Color.White // Imposta il colore dell'icona
                    )
                }

                Text(
                    text = monthNames[currentMonth],
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Button(
                    onClick = {
                        currentMonth = (currentMonth + 1).coerceAtMost(2) // Avanti di un mese
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5576B4).copy(alpha = 0.5f)),

                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Previous Month",
                        tint = Color.White // Imposta il colore dell'icona
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(310.dp)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF5576B4))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CalendarGrid(
                        daysInMonth = daysInMonths[currentMonth],
                        selectedDay = selectedDay,
                        currentMonth = currentMonth,
                        isSheetVisible = isSheetVisible,
                        events = events.groupBy { it.date.toInt() },
                        onDayClick = { day ->
                            if (selectedDay == day && isSheetVisible) {
                                isSheetVisible = false
                                selectedDay = null
                            } else {
                                selectedDay = day
                                isSheetVisible = true
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    selectedDay?.let {
                        navController.navigate("newEvent/$it/$currentMonth")
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8)),
                modifier = Modifier
                    .width(180.dp)
                    .height(45.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text("New Event", color = Color.White, fontSize = 16.sp)
            }
        }

        AnimatedVisibility(
            visible = isSheetVisible,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            SwipeableScreenContent(
                day = selectedDay,
                events = events.filter { it.date == selectedDay.toString() },
                month = currentMonth,
                isFullScreen = isFullScreen,
                onFullScreenToggle = { isFullScreen = !isFullScreen },
                onClose = { isSheetVisible = false }
            )
        }
    }
}

@Composable
fun CalendarGrid(daysInMonth: Int, events: Map<Int, List<Event>>, selectedDay: Int?,onDayClick: (Int) -> Unit, currentMonth: Int, isSheetVisible: Boolean,) {

    Column(
        horizontalAlignment = Alignment.Start
    ) {
        for (week in 0 until 5) { // Massimo 5 righe
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (week == 4) Arrangement.Start else Arrangement.SpaceEvenly
            ) {
                for (day in 1..7) {
                    val currentDay = week * 7 + day
                    if (currentDay <= daysInMonth) {
                        val backgroundColor = if (currentDay == selectedDay && isSheetVisible) {
                            Color(0xFF1F2B85) // Colore per il giorno selezionato con swipe aperto

                        } else {
                            Color.Blue.copy(alpha = 0.3f) // Colore predefinito
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
                            if (events[currentDay]?.any { it.month == currentMonth } == true) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Color.Red, CircleShape)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                        if (day < 7 && currentDay < daysInMonth) {
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

    }

    }

/*@Composable
fun SwipeableScreenContent(
    day: Int?,
    events: List<Event>,
    isFullScreen: Boolean,
    onFullScreenToggle: () -> Unit,
    onClose: () -> Unit
) {
    var offsetY by remember { mutableStateOf(0f) }
    var height by remember { mutableStateOf(350.dp) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    offsetY += delta
                    height = (600.dp - offsetY.dp).coerceAtLeast(350.dp).coerceAtMost(900.dp)
                }
            )
            .background(
                Color(0xFF5576B4),
                if (height == 900.dp) RoundedCornerShape(0.dp) else RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selected Day: ${day ?: "None"}", style = MaterialTheme.typography.headlineSmall)

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(events) { event ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Time: ${event.time}", color = Color.Gray)
                        Text("Quantity: ${event.quantity}", color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
*/

@Composable
fun SwipeableScreenContent(
    day: Int?,
    month: Int,
    events: List<Event>,
    isFullScreen: Boolean,
    onFullScreenToggle: () -> Unit,
    onClose: () -> Unit
) {
    var offsetY by remember { mutableStateOf(150f) }
    var height by remember { mutableStateOf(350.dp) }

    val filteredEvents = events.filter { it.date == day.toString() && it.month == month }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    offsetY += delta
                    height = (600.dp - offsetY.dp).coerceAtLeast(350.dp).coerceAtMost(900.dp)
                }
            )
            .background(
                Color(0xFF5576B4),
                if (height == 900.dp) RoundedCornerShape(0.dp) else RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selected Day: ${day ?: "None"}", style = MaterialTheme.typography.headlineSmall)

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredEvents) { event ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Time: ${event.time}", color = Color.Gray)
                        Text("Quantity: ${event.quantity}", color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

