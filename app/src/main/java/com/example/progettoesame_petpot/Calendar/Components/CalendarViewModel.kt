package com.example.progettoesame_petpot.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.progettoesame_petpot.model.Feed
import com.example.progettoesame_petpot.model.PetPotModel

import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel : ViewModel() {

    private val petPotModel = PetPotModel()
    private val _feedDays = mutableStateOf(setOf<Date>()) // Set to store feed days
    private val calendar = Calendar.getInstance()
    val errorMessage = mutableStateOf<String?>(null)
    // Usa mutableStateOf per il recompose immediato
    val _selectedStartDate = mutableStateOf<Date?>(null)
    val selectedStartDate: Date? get() = _selectedStartDate.value
    val _selectedEndDate = mutableStateOf<Date?>(null)
    val selectedEndDate: Date? get() = _selectedEndDate.value
    private val _currentMonth = mutableStateOf(calendar.get(Calendar.MONTH))
    val currentMonth: Int get() = _currentMonth.value
    private val _currentYear = mutableStateOf(calendar.get(Calendar.YEAR))
    val currentYear: Int get() = _currentYear.value




    val today = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)


    }.time

    fun normalizeDate(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }


    fun loadFeedDays() {
        petPotModel.getFeeds { feeds ->
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val markedDays = mutableSetOf<Date>()

            feeds.forEach { feed ->
                val startDate = dateFormat.parse(feed.dateStart)
                val endDate = dateFormat.parse(feed.dateEnd)

                Log.d("FeedDebug", "Feed Start: ${feed.dateStart} | Parsed: $startDate")
                Log.d("FeedDebug", "Feed End: ${feed.dateEnd} | Parsed: $endDate")

                if (startDate != null && endDate != null) {
                    val calendar = Calendar.getInstance()
                    calendar.time = startDate

                    while (!calendar.time.after(endDate)) {
                        val normalizedDay = normalizeDate(calendar.time)
                        Log.d("FeedDebug", "Marked Day (Normalized): $normalizedDay")
                        markedDays.add(normalizedDay)
                        calendar.add(Calendar.DAY_OF_MONTH, 1)
                    }
                }
            }

            _feedDays.value = markedDays
        }
    }
    fun isFeedDay(date: Date): Boolean {
        val normalizedDate = normalizeDate(date)
        val result = _feedDays.value.contains(normalizedDate)
        Log.d("FeedDebug", "Checking Date (Normalized): $normalizedDate | Result: $result")
        return result
    }

    fun getFeedsForDate(date: String, callback: (List<Feed>) -> Unit) {
        petPotModel.getFeeds { feeds ->
            val filteredFeeds = feeds.filter {
                val startDate = normalizeDate(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(it.dateStart)!!)
                val endDate = normalizeDate(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(it.dateEnd)!!)
                val targetDate = normalizeDate(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date)!!)
                targetDate in startDate..endDate
            }
            callback(filteredFeeds)
        }
    }


    // Attributi del feed
    val feedOrarioFisso = mutableStateOf("12:00")
    val feedQuantita = mutableStateOf(100f)

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


    fun selectDay(day: Int) {

        calendar.set(Calendar.YEAR, _currentYear.value)
        calendar.set(Calendar.MONTH, _currentMonth.value)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        val selectedDate = calendar.time


// controllo che il giorno che sto selezionando non sia oggi
        Log.d("CalendarViewModel", "Today: $today")
        if (selectedDate.before(today)) {
            Log.d("Feed", "Data non valida, non puoi selezionare il passato")
            return // Blocca la selezione di date passate
        }

        if (_selectedStartDate.value == null) {
            _selectedStartDate.value = selectedDate
            _selectedEndDate.value = null

        } else if (_selectedEndDate.value == null) {
            if (selectedDate.after(_selectedStartDate.value)) {
                _selectedEndDate.value = selectedDate
            } else {
                _selectedStartDate.value = selectedDate
                _selectedEndDate.value = null
            }
        } else {
            _selectedStartDate.value = selectedDate
            _selectedEndDate.value = null
        }

        Log.d("Feed", "Selected Start Date: ${_selectedStartDate.value}")
        Log.d("Feed", "Selected End Date: ${_selectedEndDate.value}")
    }

    fun completeSelection() {
        if (_selectedStartDate.value != null && _selectedEndDate.value == null) {
            // If only start date is selected, set end date equal to start date
            _selectedEndDate.value = _selectedStartDate.value
        }

        // Now you can proceed with the selected date(s)
        val startDate = _selectedStartDate.value
        val endDate = _selectedEndDate.value

        Log.d("Selected Dates", "Start: $startDate, End: $endDate")
    }


    fun getDaysInCurrentMonth(): Int {
        return if (_currentMonth.value == 1 && isLeapYear(_currentYear.value)) 29 else daysInMonths[_currentMonth.value]
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    fun isDateSelected(date: Date, startDate: Date?, endDate: Date?): Boolean {
        if (startDate == null) return false

        // Normalizziamo la data eliminando ore, minuti e secondi, cosi non si confonda quale giorno viene selezionato
        fun normalize(date: Date): Calendar {
            return Calendar.getInstance().apply {
                time = date
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        }

        val normalizedDate = normalize(date)
        val normalizedStart = normalize(startDate)
        val normalizedEnd = if (endDate != null) normalize(endDate) else normalizedStart

        return !normalizedDate.before(normalizedStart) && !normalizedDate.after(normalizedEnd)
    }

    fun getStartAndEndDate(): Pair<Date?, Date?> {
        return Pair(_selectedStartDate.value, _selectedEndDate.value)
    }

    // Funzione per aggiornare l'orario fisso del feed
    fun updateFeedOrarioFisso(time: String) {
        feedOrarioFisso.value = time
    }

    // Funzione per aggiornare la quantità del feed
    fun updateFeedQuantita(quantity: Float) {
        feedQuantita.value = quantity
    }


    // Funzione per salvare il feed
    fun saveFeed() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedStartDate = dateFormat.format(selectedStartDate?.time)
        val formattedEndDate = dateFormat.format(selectedEndDate?.time)

        petPotModel.getFeeds { existingFeeds ->
            val alreadyExists = existingFeeds.any {
                it.dateStart == formattedStartDate && it.timeFix == feedOrarioFisso.value
            }

            if (alreadyExists) {
                errorMessage.value = "A feed already exists for this date and time!"
                Log.d("Feed", "Esiste già un feed per questa data e ora!")
                return@getFeeds
            }

            // Creazione del feed
            val feed = Feed(
                timeFix = feedOrarioFisso.value,
                dateStart = formattedStartDate ?: "",
                dateEnd = formattedEndDate ?: "",
                quantity = feedQuantita.value,
            )

            petPotModel.saveFeed(feed)
            Log.d("Feed", "Feed salvato con successo: $feed")
            errorMessage.value = null
        }
    }

    // Funzione per ripristinare i dati salvati (se necessario)
    fun restoreData(feed: Feed) {
        feedOrarioFisso.value = feed.timeFix
        feedQuantita.value = feed.quantity
        _selectedStartDate.value = Date(feed.dateStart)
        _selectedEndDate.value = if (feed.dateEnd > 0.toString()) Date(feed.dateEnd) else null
    }

    private val _errorCreationFeed = MutableLiveData<String?>()
    val errorCreationFeed: LiveData<String?> get() = _errorCreationFeed

    fun validateAndSaveFeed() {
        if (selectedStartDate == null || selectedEndDate == null) {
            _errorCreationFeed.value = "Please select both start and end dates."
        } else if (feedQuantita.value == null || feedOrarioFisso.value == null) {
            _errorCreationFeed.value = "Please select both quantity and time."
        } else {
            saveFeed()
            _errorCreationFeed.value = null
        }
    }
}



