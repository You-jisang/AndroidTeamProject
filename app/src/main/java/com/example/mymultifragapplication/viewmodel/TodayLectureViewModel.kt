package com.example.mymultifragapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymultifragapplication.repository.LectureRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// ViewModel 상속 -> 데이터와 ui 분리하여 관리
class TodayLectureViewModel : ViewModel() {
    private val repository = LectureRepository()

    //MutableLiveData는 데이터 변경 가능
    //LiveData는 데이터 변경 불가능
    private val _lectures = MutableLiveData<List<Lecture>>()
    val lectures: LiveData<List<Lecture>> get() = _lectures

    init {
        updateLecturesForToday()
    }

    //요일 문자열로 반환
    private fun getWeekday(calendar: Calendar): String {
        val format = SimpleDateFormat("EEEE", Locale.US)
        return format.format(calendar.time).uppercase(Locale.US)
    }

    private fun updateLecturesForToday() {
        // 오늘 요일 문자열로 저장
        val weekday = getWeekday(Calendar.getInstance())

        // 변경이 감지되면 Repository에서 반환한 LiveData(lectures)를 MutableLiveData인 _lectures에 저장한다.
        repository.getLectures(weekday) { lectures ->
            _lectures.value = lectures
        }
    }


}
