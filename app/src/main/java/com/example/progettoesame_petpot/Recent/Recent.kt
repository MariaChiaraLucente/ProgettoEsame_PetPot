package com.example.progettoesame_petpot.Recent

import BottomNavBar
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.progettoesame_petpot.model.Meal
import com.example.progettoesame_petpot.viewmodel.RecentFeedsViewModel
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun RecentFeedsScreen(navController: NavController, viewModel: RecentFeedsViewModel = viewModel()) {
    val feeds by viewModel.recentFeeds.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF5576B4))
    ) {
        Column (
            modifier = Modifier.height(700.dp).padding(16.dp).fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Recent Feeds",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp).align( Alignment.CenterHorizontally),
                color = Color.White
            )

            LazyColumn {
                items(feeds) { feed ->
                    FeedCard(feed)
                }
            }
        }
        BottomNavBar(
            selectedScreen = "Recent",
            onScreenSelected = { navController.navigate(it) }
        )
    }
}

@Composable
fun FeedCard(feed: Meal) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.background(Color(0xFF8099C9)).fillMaxWidth().padding(16.dp)
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
