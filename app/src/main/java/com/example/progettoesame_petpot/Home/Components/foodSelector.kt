package com.example.progettoesame_petpot.Home.Components
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.progettoesame_petpot.R

@Composable
fun FoodSelector(selectedFood: String, onFoodSelected: (String) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    val foodOptions = listOf(
        "Meat" to R.drawable.beef,
        "Chicken" to R.drawable.chicken,
        "Carrot" to R.drawable.carrot,
        "Pork" to R.drawable.pig
    )

    val selectedFoodImage = foodOptions.find { it.first == selectedFood }?.second ?: R.drawable.beef

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ✅ Mostra le opzioni SOLO quando il menu è espanso
        AnimatedVisibility(visible = isExpanded) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                foodOptions.forEach { (foodName, foodImage) ->
                    if (foodName != selectedFood) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color.White, shape = CircleShape)
                                .clickable {
                                    onFoodSelected(foodName) // ✅ Cambia il cibo selezionato
                                    isExpanded = false // ✅ Chiudi il menu
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = foodImage),
                                contentDescription = foodName,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            }
        }

        // ✅ Pallino principale che resta fisso e cambia colore quando cliccato
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(if (isExpanded) Color.Gray else Color.LightGray, shape = CircleShape)
                .clickable { isExpanded = !isExpanded },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = selectedFoodImage),
                contentDescription = selectedFood,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}




