package com.example.progettoesame_petpot.Calendar.Components

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.progettoesame_petpot.model.Feed
import com.example.progettoesame_petpot.model.PetPotModel
import java.util.Date

class EditViewModel : ViewModel() {
    private val petPotModel = PetPotModel()
    private val _feed = MutableLiveData<Feed>()
    val feed: LiveData<Feed> get() = _feed

    fun setFeed(feed: Feed) {
        _feed.value = feed
    }

    fun updateFeed(
        userId: String,
        feedId: String,
        newQuantity: Float,
        newTimeFix: String,
        newDate: Date,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        petPotModel.updateProgrammedFeed(
            userId,
            feedId,
            newQuantity,
            newTimeFix,
            newDate,
            onSuccess,
            onFailure
        )
    }

    fun getFeedById(feedId: String) {
        petPotModel.getFeedById(feedId) { feed ->
            feed?.let {
                _feed.value = it
            }
        }
    }

}
