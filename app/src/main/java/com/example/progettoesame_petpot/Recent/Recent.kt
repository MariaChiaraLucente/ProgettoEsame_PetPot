package com.example.progettoesame_petpot.Recent

import BottomNavBar
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.progettoesame_petpot.model.Feed
import com.example.progettoesame_petpot.model.Meal
import com.example.progettoesame_petpot.viewmodel.RecentFeedsViewModel
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RecentFeedsScreen(navController: NavController, viewModel: RecentFeedsViewModel = viewModel()) {

    var selectedSection by remember { mutableStateOf(0) } // 0 per la prima sezione, 1 per la seconda
    var previousSection by remember { mutableStateOf(0) }
    var swipeDirection by remember { mutableStateOf(0) } // -1 per swipe a sinistra, 1
    val meals by viewModel.recentMeals.collectAsState()
    val feeds by viewModel.completedFeeds.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5576B4))
    ) {
        Column(
            modifier = Modifier
                .height(700.dp)
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Recent Feeds",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        var sectionChanged = false

                        detectHorizontalDragGestures(
                            onDragEnd = {
                                sectionChanged = false
                            }
                        ) { change, dragAmount ->
                            change.consume()

                            val swipeThreshold = 0

                            if (!sectionChanged) {
                                if (dragAmount > swipeThreshold && selectedSection > 0) {
                                    swipeDirection = -1
                                    selectedSection--
                                    sectionChanged = true
                                } else if (dragAmount < -swipeThreshold && selectedSection < 1) {
                                    swipeDirection = 1
                                    selectedSection++
                                    sectionChanged = true
                                }
                            }
                        }
                    }
            ) {
                LaunchedEffect(selectedSection) {
                    previousSection = selectedSection // Aggiorna solo dopo il cambio di sezione
                }

                AnimatedContent(
                    targetState = selectedSection,
                    transitionSpec = {
                        when {
                            selectedSection > previousSection ->
                                slideInHorizontally { width -> width } with slideOutHorizontally { width -> -width }

                            selectedSection < previousSection ->
                                slideInHorizontally { width -> -width } with slideOutHorizontally { width -> width }

                            else -> fadeIn() with fadeOut()
                        }
                    }
                ) { section ->
                    when (section) {
                        0 -> Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFDFCF8))
                        ) {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
//                                Image(
//                                    painter = painterResource(id = R.drawable.ic_feed), // Icona per i feed
//                                    contentDescription = null,
//                                    modifier = Modifier
//                                        .size(55.dp)
//                                        .padding(start = 16.dp, top = 8.dp, end = 8.dp)
//                                )
                                    Text(
                                        text = "Completed Feeds",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 25.sp,
                                        color = Color(0xFF346E60),
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                                LazyColumn(modifier = Modifier.fillMaxSize()) {
                                    items(feeds) { feed ->
                                        FeedCard(feed)
                                    }
                                }
                            }
                        }

                        1 -> Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFDFCF8))
                        ) {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
//                                Image(
//                                    painter = painterResource(id = R.drawable.ic_completed), // Icona per i feed completati
//                                    contentDescription = null,
//                                    modifier = Modifier
//                                        .size(55.dp)
//                                        .padding(start = 16.dp, top = 8.dp, end = 8.dp)
//                                )
                                    Text(
                                        text = "QuickFeeds",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 25.sp,
                                        color = Color(0xFF346E60),
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                                LazyColumn(modifier = Modifier.fillMaxSize()) {
                                    items(meals) { meal ->
                                        MealCard(meal)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            BottomNavBar(
                selectedScreen = "Recent",
                onScreenSelected = { navController.navigate(it) }
            )
        }
    }
}

    @Composable
    fun MealCard(feed: Meal) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFF8099C9))
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Date: ${SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date(feed.timestamp))}", // 🆕 Mostra la data e ora formattata
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Quantity: ${feed.quantity}g",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4246BD)
                )
            }
        }
    }

    @Composable
    fun FeedCard(feed: Feed) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFF8099C9))
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Date: ${SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date(feed.timestamp))}", // 🆕 Mostra la data e ora formattata
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Quantity: ${feed.quantity}g",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4246BD)
                )
            }
        }
    }