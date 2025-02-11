package com.example.progettoesame_petpot.Home.Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.progettoesame_petpot.R

enum class FoodSelectorOrientation {
    VERTICAL, HORIZONTAL
}

@Composable
fun FoodSelector(
    selectedFood: String,
    onFoodSelected: (String) -> Unit,
    orientation: FoodSelectorOrientation = FoodSelectorOrientation.VERTICAL
) {
    var isExpanded by remember { mutableStateOf(false) }
    var currentFood by remember { mutableStateOf(selectedFood) } // 🔥 Assicura che il pallino si aggiorni subito

    val foodOptions = listOf(
        "Meat" to R.drawable.beef,
        "Chicken" to R.drawable.chicken,
        "Vegetables" to R.drawable.carrot,
        "Pork" to R.drawable.pig
    )

    val selectedFoodImage = foodOptions.find { it.first == currentFood }?.second ?: R.drawable.beef

    if (orientation == FoodSelectorOrientation.VERTICAL) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedVisibility(visible = isExpanded) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    foodOptions.forEach { (foodName, foodImage) ->
                        if (foodName != currentFood) {
                            FoodOption(foodImage, foodName) {
                                currentFood = foodName  // 🔥 Aggiorna subito il pallino principale
                                onFoodSelected(foodName)
                                isExpanded = false
                            }
                        }
                    }
                }
            }
            MainFoodButton(selectedFoodImage, isExpanded) { isExpanded = !isExpanded }
        }
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            MainFoodButton(selectedFoodImage, isExpanded) { isExpanded = !isExpanded }
            AnimatedVisibility(visible = isExpanded) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    foodOptions.forEach { (foodName, foodImage) ->
                        if (foodName != currentFood) {
                            FoodOption(foodImage, foodName) {
                                currentFood = foodName  // 🔥 Aggiorna subito il pallino principale
                                onFoodSelected(foodName)
                                isExpanded = false
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainFoodButton(selectedFoodImage: Int, isExpanded: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(if (isExpanded) Color.Gray else Color.LightGray, shape = CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = selectedFoodImage),
            contentDescription = "Selected Food",
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun FoodOption(foodImage: Int, foodName: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(Color.White, shape = CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = foodImage),
            contentDescription = foodName,
            modifier = Modifier.size(40.dp)
        )
    }
}
