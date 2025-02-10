package com.example.progettoesame_petpot.QuickFeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.progettoesame_petpot.model.Meal
import com.example.progettoesame_petpot.model.PetPotModel
import kotlinx.coroutines.launch
import java.util.Date

class QuickFeedViewModel : ViewModel() {
    private val petPotModel = PetPotModel()

    var foodQuantity = 0
        private set

    var foodType = ""
        private set

    fun setFoodQuantity(quantity: Int) {
        foodQuantity = quantity
    }

    fun setFoodType(type: String) {
        foodType = type
    }

    fun saveMeal(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val currentTime = System.currentTimeMillis()
        if (foodQuantity == 0 || foodType.isEmpty()) {
            onFailure("Seleziona quantità e tipo di cibo!")
            return
        }

        val meal = Meal(
            description = "Pasto veloce",
            timeFix = Date(currentTime).toString(),
            dateStart = Date(currentTime),
            dateEnd = Date(currentTime),
            quantity = foodQuantity.toFloat(),
            timestamp = currentTime
        )

        viewModelScope.launch {
            petPotModel.saveMeal(meal, onSuccess, onFailure)
        }
    }
}
