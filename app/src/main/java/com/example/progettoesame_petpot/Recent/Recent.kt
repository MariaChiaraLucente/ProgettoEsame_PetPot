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

    var selectedTabIndex by remember { mutableStateOf(0) } // 0: Completed Feeds, 1: Quick Feeds
    val meals by viewModel.recentMeals.collectAsState()
    val feeds by viewModel.completedFeeds.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.height(26.dp))
        Column(
            modifier = Modifier
                .height(531.dp)
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            // 🔹 Titolo
            Text(
                text = "Recent Feeds",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 TAB BAR
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Completed Feeds") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Quick Feeds") }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 CONTENUTO DELLA TAB SELEZIONATA
            when (selectedTabIndex) {
                0 -> CompletedFeedsSection(feeds)
                1 -> QuickFeedsSection(meals)
            }
        }
        BottomNavBar(
            selectedScreen = "Recent",
            onScreenSelected = { navController.navigate(it) }
        )
    }
}

// 🔹 SEZIONE COMPLETED FEEDS
@Composable
fun CompletedFeedsSection(feeds: List<Feed>) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(feeds) { feed ->
                FeedCard(feed)
            }
        }
    }
}

// 🔹 SEZIONE QUICK FEEDS
@Composable
fun QuickFeedsSection(meals: List<Meal>) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(meals) { meal ->
                MealCard(meal)
            }
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
                .background(MaterialTheme.colorScheme.secondary)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${SimpleDateFormat("dd/MM/yyyy - HH:mm").format(Date(feed.timestamp))}", // 🆕 Mostra la data e ora formattata
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "${feed.quantity} g - ${feed.description}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF5D60B0)
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
                .background(MaterialTheme.colorScheme.secondary)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${SimpleDateFormat("dd/MM/yyyy").format(feed.dateStart)} - ${feed.timeFix}", // 🆕 Mostra la data e ora formattata
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "Quantity: ${feed.quantity}g",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF5D60B0)
            )
        }
    }
}