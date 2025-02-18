package com.example.progettoesame_petpot.ui

import BottomNavBar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
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
    LaunchedEffect(Unit) {
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

    Text(
        text = "Calendar",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.padding(top = 20.dp, start = 130.dp),
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(559.dp)
                .padding(15.dp),
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { calendarViewModel.changeMonth(forward = false) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Previous Month",
                        tint = Color(0xFFC6E9EB)
                    )
                }

                Text(
                    text = "${monthNames[currentMonth]} $currentYear",
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 22.5.sp,
                    color = Color(0xFFD5EDED)

                )

                IconButton(
                    onClick = { calendarViewModel.changeMonth(forward = true) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Next Month",
                        tint = Color(0xFFC6E9EB)
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

            Spacer(modifier = Modifier.height(2.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Button(
                    onClick = {
                        onNavigateToFeedCreation()
//                    calendarViewModel.completeSelection()
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
                    modifier = Modifier.border(2.dp, color = Color(0xFF0A0A0A), CircleShape),

                    ) {
                    Text(text = "+ New Event", color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(3.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        showDeleteDialog = true
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xffca413f)),
                    modifier = Modifier.border(3.dp, color = Color(0xFF5C0303), CircleShape),
                ) {
                    Text(text = "Clear all events", color = Color.White, fontSize = 13.sp, modifier = Modifier.padding(3.dp))
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
    val todayCalendar = Calendar.getInstance()

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(330.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(5.dp)
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
                            val isPastDate = currentDate.before(todayCalendar.time)
                            val isToday = calendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR) &&
                                    calendar.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH) &&
                                    calendar.get(Calendar.DAY_OF_MONTH) == todayCalendar.get(Calendar.DAY_OF_MONTH)

                            Column {
                                Box(
                                    modifier = Modifier
                                        .size(35.dp)
                                        .background(
                                            when {
                                                isToday -> Color(0xFF2E3957)
                                                isPastDate -> Color(0xFF8791A2)
                                                else -> MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
                                            },
                                            CircleShape
                                        )
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
                                        color = when{
                                            isToday -> Color.White
                                            isPastDate -> Color(0xFFC2C0C0)
                                            else -> Color.White.copy(alpha = 0.7f)
                                        },
                                        fontSize = 16.sp
                                    )
                                }

                                // Aggiunge il pallino sotto i giorni con feed
                                if (hasFeed) {
                                    Spacer(modifier = Modifier.height(1.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(Color(0xffca413f), CircleShape)
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
                Spacer(modifier = Modifier.height(15.dp))
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
            title = { Text(text = "Confirm Clear", color = MaterialTheme.colorScheme.onBackground) },
            containerColor = MaterialTheme.colorScheme.secondary,
            textContentColor = MaterialTheme.colorScheme.onBackground,
            text = { Text(text = "Are you sure you want to delete all programmed feeds?") },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("Yes", color = Color(0xFF227D33))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("No", color = Color(0xFFA72626))
                }
            }
        )
    }
}






