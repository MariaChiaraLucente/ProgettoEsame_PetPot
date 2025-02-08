package com.example.progettoesame_petpot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.progettoesame_petpot.viewmodel.CalendarViewModel
import java.util.Calendar
import java.util.Date

@Composable
fun CalendarScreen(

    calendarViewModel: CalendarViewModel,
    onNavigateToFeedCreation: () -> Unit)
{

    val currentMonth = calendarViewModel.currentMonth
    val currentYear = calendarViewModel.currentYear
    val selectedStartDate = calendarViewModel.selectedStartDate
    val selectedEndDate = calendarViewModel.selectedEndDate

//

    val monthNames = listOf("January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December")

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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { calendarViewModel.changeMonth(forward = false) },
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Previous Month")
                }

                Text(
                    text = "${monthNames[currentMonth]} $currentYear",
                    style = MaterialTheme.typography.headlineSmall
                )

                IconButton(
                    onClick = { calendarViewModel.changeMonth(forward = true) },
                ) {
                    Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Next Month")
                }
            }

            CalendarGrid(
                calendarViewModel = calendarViewModel,
                daysInMonth = calendarViewModel.getDaysInCurrentMonth(),
                currentMonth = currentMonth,
                currentYear = currentYear,
                selectedStartDate = selectedStartDate,
                selectedEndDate = selectedEndDate,
                onDayClick = { day -> calendarViewModel.selectDay(day) }
            )


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onNavigateToFeedCreation() },
                shape = CircleShape
            ) {
                Text(text = "New Event")
            }
        }
    }
}



@Composable
fun CalendarGrid(
    calendarViewModel: CalendarViewModel,
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
        Column( verticalArrangement = Arrangement.Center,
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
                            val isSelected = calendarViewModel.isDateSelected(currentDate, selectedStartDate, selectedEndDate)

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






