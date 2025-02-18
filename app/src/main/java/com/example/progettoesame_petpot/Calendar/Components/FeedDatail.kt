package com.example.progettoesame_petpot.Calendar.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.progettoesame_petpot.R
import com.example.progettoesame_petpot.model.Feed
import com.example.progettoesame_petpot.viewmodel.CalendarViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun FeedDetailScreen(
    navController: NavController,
    calendarViewModel: CalendarViewModel,
    selectedDate: Date
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(selectedDate)

    var feedsForDate by remember { mutableStateOf(emptyList<Feed>()) }

    LaunchedEffect(selectedDate) {
        calendarViewModel.getFeedsForDate(formattedDate) { feeds ->
            feedsForDate = feeds
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
                                .background (MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                Text(
                    text = "$formattedDate",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,


                )
            }

            if (feedsForDate.isEmpty()) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "No feeds available for this date",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxSize().padding(50.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(feedsForDate) { feed ->
                            //Spacer(modifier = Modifier.padding(8.dp))
                            FeedCard(feed, navController = navController)
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun FeedCard(feed: Feed, calendarViewModel: CalendarViewModel = CalendarViewModel(), eventViewModel: EventViewModel = EventViewModel(), navController: NavController) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Time: ${feed.timeFix}",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Quantity: ${feed.quantity}g",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "Edit",
                        modifier = Modifier.size(35.dp)
                            .clickable(onClick = {
                                navController.navigate("EditFeed/${feed.id}")
                            },)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Image(
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = "Delete",
                        modifier = Modifier.size(35.dp)
                            .clickable(onClick = { showDeleteDialog = true })
                    )

                }
            }
        }
    }

    if (showDeleteDialog) {
        ConfirmDeleteDialog(
            showDialog = showDeleteDialog,
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                showDeleteDialog = false
                calendarViewModel.deleteFeed(feed)
                calendarViewModel.loadFeedDays() // Aggiorna la griglia
                navController.navigate("calendar")
            }
        )
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
            title = { Text(text = "Confirm Delete", color = MaterialTheme.colorScheme.onBackground) },
            containerColor = MaterialTheme.colorScheme.secondary,
            textContentColor = MaterialTheme.colorScheme.onBackground,
            text = { Text(text = "Are you sure you want to delete this feed?") },
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

