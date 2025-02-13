package com.example.progettoesame_petpot.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.progettoesame_petpot.model.Feed
import com.example.progettoesame_petpot.model.PetPotModel
import kotlinx.coroutines.launch

import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel : ViewModel() {

    private val petPotModel = PetPotModel()
    private val _feedDays = mutableStateOf(setOf<Date>()) // Set to store feed days
    private val calendar = Calendar.getInstance()
    // Usa mutableStateOf per il recompose immediato
    val _selectedStartDate = mutableStateOf<Date?>(null)
    val selectedStartDate: Date? get() = _selectedStartDate.value
    val _selectedEndDate = mutableStateOf<Date?>(null)
    val selectedEndDate: Date? get() = _selectedEndDate.value
    private val _currentMonth = mutableStateOf(calendar.get(Calendar.MONTH))
    val currentMonth: Int get() = _currentMonth.value
    private val _currentYear = mutableStateOf(calendar.get(Calendar.YEAR))
    val currentYear: Int get() = _currentYear.value


    fun normalizeDate(date: Date): Date {
        val calendar = Calendar.getInstance().apply{
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.time
    }


    fun loadFeedDays() {
        petPotModel.getFeeds { feeds ->
//            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val markedDays = mutableSetOf<Date>()
// Filtra i feed con stato "Programmed"
            val programmedFeeds = feeds.filter { it.status == "Programmed" }

            programmedFeeds.forEach { feed ->
                val startDate = feed.dateStart
                val endDate = feed.dateEnd

                Log.d("FeedDebug", "Feed Start: ${feed.dateStart} | Parsed: $startDate")
                Log.d("FeedDebug", "Feed End: ${feed.dateEnd} | Parsed: $endDate")

                if (startDate != null && endDate != null) {
                    val calendar = Calendar.getInstance()
                    calendar.time = startDate

                    while (!calendar.time.after(endDate)) {

                        Log.d("FeedDebug", "Marked Day (Normalized): ${normalizeDate(calendar.time)}")
                        markedDays.add(normalizeDate(calendar.time))
                        calendar.add(Calendar.DAY_OF_MONTH, 1)
                    }
                }
            }

            _feedDays.value = markedDays
        }
    }

    fun isFeedDay(date: Date): Boolean {
      return  _feedDays.value.contains(normalizeDate(date))
        Log.d("FeedDebug", "Checking Date (Normalized):  | Result: ${_feedDays.value.contains(normalizeDate(date))}")

    }

    fun getFeedsForDate(date: String, callback: (List<Feed>) -> Unit) {
        petPotModel.getFeeds { feeds ->
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val targetDate = normalizeDate(dateFormat.parse(date)!!)

            val filteredFeeds = feeds.filter { feed ->
                val startDate = feed.dateStart?.let { normalizeDate(it) }
                val endDate = feed.dateEnd?.let { normalizeDate(it) }

                startDate != null && endDate != null && targetDate in startDate..endDate
            }
            callback(filteredFeeds)
        }
    }


    private val daysInMonths = listOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    fun changeMonth(forward: Boolean) {
        if (forward) {
            if (_currentMonth.value == 11) { // Dec → Jan
                _currentMonth.value = 0
                _currentYear.value += 1
            } else {
                _currentMonth.value += 1
            }
        } else {
            if (_currentMonth.value == 0) { // Jan → Dec
                _currentMonth.value = 11
                _currentYear.value -= 1
            } else {
                _currentMonth.value -= 1
            }
        }
    }

    fun getDaysInCurrentMonth(): Int {
        return if (_currentMonth.value == 1 && isLeapYear(_currentYear.value)) 29 else daysInMonths[_currentMonth.value]
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }


    fun deleteFeed(feed: Feed) {
        petPotModel.deleteFeed(feed)
    }

    fun deleteAllProgrammedFeeds() {
        viewModelScope.launch {
            petPotModel.getFeeds { feeds ->
                val programmedFeeds = feeds.filter { it.status == "Programmed" }
                programmedFeeds.forEach { feed ->
                    petPotModel.deleteFeed(feed)
                    loadFeedDays()
                }
            }
        }
    }



}



