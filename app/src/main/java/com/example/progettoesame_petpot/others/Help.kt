package com.example.progettoesame_petpot.others

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(navController: NavController) {
    val faqs = listOf(
        "How do I register?" to "To register, simply enter a username and password on the registration screen, then follow the steps to complete your profile.",
        "How do I schedule a meal?" to "Go to the calendar section, select a date and time, then set the quantity and type of food you want to schedule.",
        "How do I check the meal history?" to "You can view past meals in the \"Recent\" section, where you’ll find all completed Quick Feeds and scheduled meals.",
        "How do I switch to Dark Mode?" to "If you want to change it manually, go to the settings menu and toggle the \"Dark Mode\" switch.",
        "What is Quick Feed and how does it work?" to "Quick Feed allows you to instantly dispense food for your pet without scheduling it. Simply go to the Quick Feed section, select the amount and type of food, then press \"Feed\".",
        "Can I cancel or modify a scheduled meal?" to "Yes! Go to the calendar section, find the scheduled meal, and tap on it to edit or delete it.",
        "How do I know if the meal has been dispensed?" to "After each meal (scheduled or Quick Feed), you can check the \"Recent\" section to confirm that the feeding was successful."
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FAQ", color = MaterialTheme.colorScheme.onBackground) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            Text("Frequently Asked Questions", fontSize = 22.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            faqs.forEach { (question, answer) ->
                ExpandableFAQCard(question, answer)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ExpandableFAQCard(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        onClick = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = question, color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = answer, color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp)
            }
        }
    }
}
