package com.example.progettoesame_petpot.QuickFeed

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.progettoesame_petpot.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickFeed(navController: NavController, viewModel: QuickFeedViewModel = viewModel()) {
    var showMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF5576B4))
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Quick Feed", color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF5576B4)),
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
                    text = "Choose the quantity and the type of food",
                    fontSize = 32.sp,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(70.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    FoodQuantitySelector(viewModel)
                    SemicircleLeft(
                        painter = painterResource(id = R.drawable.chicken),
                        modifier = Modifier.align(Alignment.CenterStart).offset(x = (-235).dp),
                        onClick = { viewModel.setFoodType("Chicken") },
                        foodType = viewModel.foodType,
                        type = "Chicken"
                    )
                    SemicircleRight(
                        painter = painterResource(id = R.drawable.beef),
                        modifier = Modifier.align(Alignment.CenterEnd).offset(x = (235).dp),
                        onClick = { viewModel.setFoodType("Beef") },
                        foodType = viewModel.foodType,
                        type = "Beef"
                    )
                }
                Spacer(modifier = Modifier.height(56.dp))
                Button(
                    onClick = {
                        viewModel.saveMeal(
                            onSuccess = { navController.navigate("Drawers") },
                            onFailure = { showMessage = it }
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E3EB8), contentColor = Color.White),
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = Modifier.width(162.dp).height(68.dp)
                ) {
                    Text("Feed", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                if (showMessage.isNotEmpty()) {
                    Text(showMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}

@Composable
fun FoodQuantitySelector(viewModel: QuickFeedViewModel) {
    Card(
        modifier = Modifier.width(195.dp).height(300.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth().background(Color(0xFFA2B0CA))) {
            items(50) { index ->
                val grams = (index + 1) * 5
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { viewModel.setFoodQuantity(grams) }
                        .background(if (grams == viewModel.foodQuantity) Color(0xFF7F96C1) else Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("$grams g", modifier = Modifier.padding(16.dp), fontSize = 24.sp, color = Color(0xFF2F34BE), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}


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