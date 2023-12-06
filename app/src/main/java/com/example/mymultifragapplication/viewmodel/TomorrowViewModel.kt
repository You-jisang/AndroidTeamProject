package com.example.mymultifragapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymultifragapplication.repository.LectureRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        val format = SimpleDateFormat("EEEE", Locale.US) //요일 전체 이름 출력, 영어로
        return format.format(calendar.time).uppercase(Locale.US)
    }

    private fun updateLecturesForTomorrow() {
        //오늘 날짜에 1을 더해 요일로 변환(내일)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 1)

        val weekday = getWeekday(calendar)

        // 변경이 감지되면 Repository에서 반환한 LiveData(lectures)를 MutableLiveData인 _lectures에 저장한다.
        repository.getLectures(weekday) { lectures ->
            _lectures.value = lectures
        }
    }


}