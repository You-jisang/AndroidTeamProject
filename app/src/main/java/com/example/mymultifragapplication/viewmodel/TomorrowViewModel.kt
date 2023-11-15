package com.example.mymultifragapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymultifragapplication.repository.LectureRepository
import java.util.Calendar

class TomorrowViewModel : ViewModel() {
    private val repository = LectureRepository()

    private val _lectures = MutableLiveData<List<Lecture>>()
    val lectures: LiveData<List<Lecture>> get() = _lectures

    init {
        updateLecturesForTomorrow()
    }
    private fun getWeekdayFromCalendar(calendar: Calendar): String {
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "monday"
            Calendar.TUESDAY -> "tuesday"
            Calendar.WEDNESDAY -> "wednesday"
            Calendar.THURSDAY -> "thursday"
            Calendar.FRIDAY -> "friday"
            Calendar.SATURDAY -> "saturday"
            else -> "sunday"
        }
    }

    private fun updateLecturesForTomorrow() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 1)

        val weekday = getWeekdayFromCalendar(calendar)
        repository.getLectures(weekday).observeForever { lectures ->
            _lectures.value = lectures
        }
    }


}