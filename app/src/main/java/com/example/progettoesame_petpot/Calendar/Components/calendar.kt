package com.example.progettoesame_petpot


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward


@Composable
fun CalendarScreen(
    onNavigateToFeedCreation: (Int?, Int?, Int?, Int?) -> Unit // Passa giorni e mesi selezionati come argomenti
) {
    var selectedStartDay by remember { mutableStateOf<Int?>(null) }
    var selectedEndDay by remember { mutableStateOf<Int?>(null) }
    var selectedStartMonth by remember { mutableStateOf<Int?>(null) }
    var selectedEndMonth by remember { mutableStateOf<Int?>(null) }

    var currentMonth by remember { mutableStateOf(1) } // Mese corrente
    val monthNames = listOf("January", "February", "March")
    val daysInMonths = listOf(31, 28, 31) // Giorni per ciascun mese (senza considerare anni bisestili)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8099C9))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
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
                        currentMonth = (currentMonth - 1).coerceAtLeast(0) // Vai al mese precedente
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5576B4).copy(alpha = 0.5f))
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Previous Month",
                        tint = Color.White
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
                        currentMonth = (currentMonth + 1).coerceAtMost(2) // Vai al mese successivo
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5576B4).copy(alpha = 0.5f))
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Next Month",
                        tint = Color.White
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
                        selectedStartDay = selectedStartDay,
                        selectedEndDay = selectedEndDay,
                        selectedStartMonth = selectedStartMonth,
                        selectedEndMonth = selectedEndMonth,
                        currentMonth = currentMonth,
                        onDayClick = { day ->
                            if (selectedStartDay == null) {
                                // Se non è selezionato alcun giorno di inizio
                                selectedStartDay = day
                                selectedStartMonth = currentMonth
                            } else if (selectedEndDay == null) {
                                // Se il giorno di fine non è ancora selezionato
                                if (selectedStartMonth == currentMonth && day >= selectedStartDay!!) {
                                    // Caso: stesso mese e giorno selezionato dopo il giorno di inizio
                                    selectedEndDay = day
                                    selectedEndMonth = currentMonth
                                } else if (currentMonth > selectedStartMonth!!) {
                                    // Caso: mese successivo a quello di inizio
                                    selectedEndDay = day
                                    selectedEndMonth = currentMonth
                                } else {
                                    // Se il giorno selezionato è prima del giorno di inizio, resettiamo
                                    selectedStartDay = day
                                    selectedStartMonth = currentMonth
                                    selectedEndDay = null
                                    selectedEndMonth = null
                                }
                            } else {
                                // Se entrambi i giorni sono già selezionati, resettiamo
                                selectedStartDay = day
                                selectedStartMonth = currentMonth
                                selectedEndDay = null
                                selectedEndMonth = null
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onNavigateToFeedCreation(
                        selectedStartDay, selectedEndDay, selectedStartMonth, selectedEndMonth
                    )
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color(0xFF1F2B85)),
                modifier = Modifier
                    .padding(16.dp)
                    .height(50.dp)
                    .width(150.dp)
            ) {
                Text(
                    text = "New Event",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun CalendarGrid(
    daysInMonth: Int,
    selectedStartDay: Int?,
    selectedEndDay: Int?,
    selectedStartMonth: Int?, // Mese di inizio selezionato
    selectedEndMonth: Int?,   // Mese di fine selezionato
    onDayClick: (Int) -> Unit,
    currentMonth: Int
) {
    Column(horizontalAlignment = Alignment.Start) {
        for (week in 0 until 5) { // Massimo 5 righe
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (day in 1..7) {
                    val currentDay = week * 7 + day
                    if (currentDay <= daysInMonth) {
                        val isSelected = when {
                            // Giorno di inizio
                            currentDay == selectedStartDay && currentMonth == selectedStartMonth -> true
                            // Giorno di fine
                            currentDay == selectedEndDay && currentMonth == selectedEndMonth -> true
                            // Giorni nell'intervallo selezionato tra due mesi
                            selectedStartDay != null && selectedEndDay != null &&
                                    (currentMonth > selectedStartMonth!! || currentDay in selectedStartDay..(selectedEndDay ?: selectedStartDay)) -> true
                            else -> false
                        }

                        val backgroundColor = if (isSelected) {
                            Color(0xFF1F2B85)
                        } else {
                            Color.Blue.copy(alpha = 0.3f)
                        }

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
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
