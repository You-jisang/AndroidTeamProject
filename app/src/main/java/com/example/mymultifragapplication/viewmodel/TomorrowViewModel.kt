package com.example.mymultifragapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymultifragapplication.repository.LectureRepository
import java.util.Calendar

// ViewModel 상속 -> 데이터와 ui 분리하여 관리
class TomorrowViewModel : ViewModel() {
    private val repository = LectureRepository()

    //MutableLiveData는 데이터 변경 가능
    //LiveData는 데이터 변경 불가능
    private val _lectures = MutableLiveData<List<Lecture>>()
    val lectures: LiveData<List<Lecture>> get() = _lectures

    init {
        updateLecturesForTomorrow()
    }

    //요일 문자열로 반환
    private fun getWeekday(calendar: Calendar): String {
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
        //오늘 날짜에 1을 더해 요일로 변환(내알)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 1)

        val weekday = getWeekday(calendar)

        // 변경이 감지되면 Repository에서 반환한 LiveData(lectures)를 MutableLiveData인 _lectures에 저장한다.
        repository.getLectures(weekday).observeForever { lectures ->
            _lectures.value = lectures
        }
    }


}