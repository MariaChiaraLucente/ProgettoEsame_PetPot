package com.example.progettoesame_petpot.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.progettoesame_petpot.model.Feed
import com.example.progettoesame_petpot.model.Meal
import com.example.progettoesame_petpot.model.PetPotModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecentFeedsViewModel : ViewModel() {
    private val petPotModel = PetPotModel()


    private val _recentMeals = MutableStateFlow<List<Meal>>(emptyList())
    val recentMeals: StateFlow<List<Meal>> = _recentMeals

    private val _completedMeals = MutableStateFlow<List<Feed>>(emptyList())
    val completedFeeds: StateFlow<List<Feed>> = _completedMeals

    init {
        fetchRecentFeeds()
        fetchCompletedFeeds()
    }

    private fun fetchRecentFeeds() {
        viewModelScope.launch {
            petPotModel.getRecentFeeds { feeds ->
                _recentMeals.value = feeds.sortedByDescending { it.timestamp } // 🆕 Ordina per data
            }
        }
    }

    private fun fetchCompletedFeeds() {
        viewModelScope.launch {
            petPotModel.getCompletedFeeds { feeds ->
                _completedMeals.value = feeds.sortedByDescending { it.timestamp } // Ordina per data
            }
        }
    }


}
