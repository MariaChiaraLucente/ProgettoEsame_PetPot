package com.example.progettoesame_petpot.ui

import BottomNavBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.progettoesame_petpot.Calendar.Components.EventViewModel
import com.example.progettoesame_petpot.viewmodel.CalendarViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavController,
    calendarViewModel: CalendarViewModel,
    onNavigateToFeedCreation: () -> Unit
) {

//    LaunchedEffect(Unit) {
//        calendarViewModel.loadFeedDays()
//    }
    LaunchedEffect(navController.currentBackStackEntry) {
        calendarViewModel.loadFeedDays()
    }
    val currentMonth = calendarViewModel.currentMonth
    val currentYear = calendarViewModel.currentYear
    val selectedStartDate = calendarViewModel.selectedStartDate
    val selectedEndDate = calendarViewModel.selectedEndDate
    var showDeleteDialog by remember { mutableStateOf(false) }
//

    val monthNames = listOf(
        "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().height(700.dp).padding(16.dp),
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { calendarViewModel.changeMonth(forward = false) },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Previous Month",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = "${monthNames[currentMonth]} $currentYear",
                        style = MaterialTheme.typography.headlineSmall

                    )

                    IconButton(
                        onClick = { calendarViewModel.changeMonth(forward = true) },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "Next Month",
                            tint = Color.White
                        )
                    }
                }

                CalendarGrid(
                    navController = navController,
                    calendarViewModel = calendarViewModel,
                    daysInMonth = calendarViewModel.getDaysInCurrentMonth(),
                    currentMonth = currentMonth,
                    currentYear = currentYear,
                )


                Spacer(modifier = Modifier.height(36.dp))

                Column (
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally

                ){
                    Button(
                        onClick = {
                            onNavigateToFeedCreation()
//                    calendarViewModel.completeSelection()
                        },
                        shape = CircleShape
                    ) {
                        Text(text = "New Event")
                    }
                    Button(
                        onClick = {
                            showDeleteDialog = true
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text(text = "Clear all events", color = Color.White)
                    }
                }
            }
            BottomNavBar(
                selectedScreen = "calendar",
                onScreenSelected = { navController.navigate(it) }
            )
        }

    ConfirmDeleteDialog(
        showDialog = showDeleteDialog,
        onDismiss = { showDeleteDialog = false },
        onConfirm = {
            showDeleteDialog = false
            calendarViewModel.deleteAllProgrammedFeeds()
        }
    )
}


@Composable
fun CalendarGrid(
    navController: NavController, // Per la navigazione
    calendarViewModel: CalendarViewModel,
    daysInMonth: Int,
    currentMonth: Int,
    currentYear: Int,

    ) {

    val calendar = Calendar.getInstance()
    val dayNames = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(330.dp)
            .padding(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            // Intestazione con i nomi dei giorni
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

            // Griglia dei giorni
            for (week in 0 until 6) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (day in 1..7) {
                        val currentDay = week * 7 + day
                        if (currentDay <= daysInMonth) {
                            // Creiamo la data corrente
                            calendar.set(Calendar.YEAR, currentYear)
                            calendar.set(Calendar.MONTH, currentMonth)
                            calendar.set(Calendar.DAY_OF_MONTH, currentDay)
                            val currentDate = calendar.time

                            // Controlliamo se ci sono feed per questo giorno
                            val hasFeed = calendarViewModel.isFeedDay(currentDate)

                            Column {
                                Box(
                                    modifier = Modifier
                                        .size(35.dp)
                                        .background((MaterialTheme.colorScheme.background).copy(alpha = 0.3f), CircleShape)
                                        .clickable {
                                            val formattedDate = SimpleDateFormat(
                                                "yyyy-MM-dd",
                                                Locale.getDefault()
                                            ).format(currentDate)
                                            navController.navigate("feedDetail/$formattedDate")
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = currentDay.toString(),
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }

                                // Aggiunge il pallino sotto i giorni con feed
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
fun ConfirmDeleteDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Confirm Deletion") },
            text = { Text(text = "Are you sure you want to delete all programmed feeds?") },
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






