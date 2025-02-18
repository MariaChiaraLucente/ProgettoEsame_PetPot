package com.example.progettoesame_petpot.QuickFeed

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.progettoesame_petpot.AutoDismissPopup
import com.example.progettoesame_petpot.Home.Components.FoodSelector
import com.example.progettoesame_petpot.Home.Components.FoodSelectorOrientation
import com.example.progettoesame_petpot.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickFeed(navController: NavController, homeViewModel: ProfileViewModel = viewModel(), viewModel: QuickFeedViewModel = viewModel(), userId: String) {
    var showMessage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var selectedFood by remember { mutableStateOf("") }
    var foodSelected by remember { mutableStateOf(false) }

    var quantityError by remember { mutableStateOf(false) }
    var foodError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Quick Feed", color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    }
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Choose the quantity\nand the type of food",
                    fontSize = 25.sp,
                    lineHeight = 38.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(15.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    FoodQuantitySelector(viewModel)
                }

                if (quantityError) {
                    Text(
                        text = "⚠ Please select a quantity! ⚠",
                        color = Color.Yellow,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                FoodSelector(
                    //selectedFood = viewModel.foodType,
                    selectedFood = selectedFood,
                    onFoodSelected = {
                        viewModel.setFoodType(it)
                        foodSelected = true
                    },
                    orientation = FoodSelectorOrientation.HORIZONTAL
                )

                if (foodError) {
                    Text(
                        text = "⚠ Please select a food type! ⚠",
                        color = Color.Yellow,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        quantityError = viewModel.foodQuantity == 0
                        foodError = !foodSelected

                        if (!quantityError && !foodError) {
                            viewModel.saveMeal(
                                onSuccess = {
                                    homeViewModel.dispenseFood( userId = userId , viewModel.foodQuantity)
                                    showDialog = true
                                },
                                onFailure = { showMessage = it }
                            )
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .height(50.dp)
                        .width(200.dp)
                        .padding(horizontal = 16.dp)
                        .border(2.dp, Color(0xFF0A0A0A), shape = RoundedCornerShape(16.dp))
                ) {
                    Text("Feed", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                }
            }


                if (showMessage.isNotEmpty()) {
                    Text(showMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }
                if (showDialog) {
                    AutoDismissPopup(
                        message = "✅",
                        onDismiss = {
                            showDialog = false
                            navController.navigate("Drawers")
                        }
                    )
                }


            }
        }
    }

@Composable
fun FoodQuantitySelector(viewModel: QuickFeedViewModel) {
    var selectedQuantity by remember { mutableStateOf(viewModel.foodQuantity) }

    LaunchedEffect(viewModel.foodQuantity) {
        selectedQuantity = viewModel.foodQuantity
    }

    Card(
        modifier = Modifier.width(195.dp).height(300.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth().background(Color(0xFFA2B0CA))) {
            items(50) { index ->
                val grams = (index + 1) * 5
                val isSelected = grams == selectedQuantity
                Row(
                    modifier = Modifier.fillMaxWidth().clickable {
                        viewModel.setFoodQuantity(grams)
                        selectedQuantity = grams
                    }
                        .background(if (isSelected) Color(0xFF7F96C1) else Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "$grams g",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 24.sp,
                        color = Color(0xFF2F34BE),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

/*
@Composable
fun SemicircleRight(
    painter: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    foodType: String,
    type: String
) {
    Box(
        modifier = modifier
            .size(300.dp)
            .clip(GenericShape { size, _ ->
                addArc(
                    oval = Rect(0f, 0f, size.width, size.height),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 180f
                )
            })
            .background(
                if (foodType == type) Color(0xFF388E3C) else Color(0xFFA2B0CA)
            )
            .clickable { onClick(

            ) }
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(75.dp)
                .align(Alignment.CenterStart)
                .padding(start = 20.dp)
        )
    }
}

@Composable
fun SemicircleLeft(
    painter: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    foodType: String,
    type: String
) {
    Box(
        modifier = modifier
            .size(300.dp)
            .clip(GenericShape { size, _ ->
                addArc(
                    oval = Rect(0f, 0f, size.width, size.height),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 180f
                )
            })
            .background(
                if (foodType == type) Color(0xFF76A176) else Color(0xFFA2B0CA)
            )
            .clickable { onClick() }
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(75.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp)
        )
    }
}

 */