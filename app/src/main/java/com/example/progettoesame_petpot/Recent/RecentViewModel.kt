package com.example.progettoesame_petpot.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.progettoesame_petpot.model.Meal
import com.example.progettoesame_petpot.model.PetPotModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecentFeedsViewModel : ViewModel() {
    private val petPotModel = PetPotModel()
    private val _recentFeeds = MutableStateFlow<List<Meal>>(emptyList())
    val recentFeeds: StateFlow<List<Meal>> = _recentFeeds

    init {
        fetchRecentFeeds()
    }

    private fun fetchRecentFeeds() {
        viewModelScope.launch {
            petPotModel.getRecentFeeds { feeds ->
                _recentFeeds.value = feeds.sortedByDescending { it.timestamp } // 🆕 Ordina per data
            }
        }
    }
}
