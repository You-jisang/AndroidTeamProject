package com.example.mymultifragapplication.viewmodel

import androidx.lifecycle.ViewModel
import java.util.Calendar

class LectureViewModel: ViewModel(){

    //오늘 요일
    private val calendar = Calendar.getInstance()
    val todayWeekend = calendar.get(Calendar.DAY_OF_WEEK)

    //내일 요일


}