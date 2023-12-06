package com.example.mymultifragapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate


class DateViewModel : ViewModel() {

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _tomorrowDate = MutableLiveData<String>()
    val tomorrowDate: LiveData<String> = _tomorrowDate


    init {
        updateDate()
        updateTomorrowDate()
    }

    private fun updateDate() {
        val todayDate = LocalDate.now().toString()
        _date.value = todayDate

    }


    private fun updateTomorrowDate() {
        val tomorrowDate = LocalDate.now().plusDays(1).toString()
        _tomorrowDate.value = tomorrowDate
    }

}