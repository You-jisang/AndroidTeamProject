package com.example.mymultifragapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymultifragapplication.repository.LectureRepository

class ScheduleAddViewModel : ViewModel() {

    private val repository = LectureRepository()

    fun saveDataToFirebase(day: String, name: String, location: String, locationNum: String, startTimeHour: String, startTimeMin: String, endTimeHour: String, endTimeMin: String) {
        val lecture = Lecture(name, location, locationNum, startTimeHour, startTimeMin, endTimeHour, endTimeMin)

        // Day에 따라 Firebase의 데이터 구조에 추가
        repository.saveLectureToFirebase(day, lecture)
    }

    /*private fun isDayValid(day: String): Boolean {
        return day in listOf("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY")
    }*/

}