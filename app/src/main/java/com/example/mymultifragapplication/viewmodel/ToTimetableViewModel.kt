package com.example.mymultifragapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymultifragapplication.repository.LectureRepository

class ToTimetableViewModel : ViewModel() {
    private val repository = LectureRepository()

    private val _mondayLectures = MutableLiveData<List<Lecture>>()
    val mondayLectures: MutableLiveData<List<Lecture>> get() = _mondayLectures

    private val _tuesdayLectures = MutableLiveData<List<Lecture>>()
    val tuesdayLectures: MutableLiveData<List<Lecture>> get() = _tuesdayLectures

    private val _wednesdayLectures = MutableLiveData<List<Lecture>>()
    val wednesdayLectures: MutableLiveData<List<Lecture>> get() = _wednesdayLectures

    private val _thursdayLectures = MutableLiveData<List<Lecture>>()
    val thursdayLectures: MutableLiveData<List<Lecture>> get() = _thursdayLectures

    private val _fridayLectures = MutableLiveData<List<Lecture>>()
    val fridayLectures: MutableLiveData<List<Lecture>> get() = _fridayLectures

    init {
        updateLecturesForMonday()
        updateLecturesForTuesday()
        updateLecturesForWednesday()
        updateLecturesForThursday()
        updateLecturesForFriday()
    }

    fun updateLecturesForMonday() {
        // 오늘 요일 문자열로 저장
        //val weekday = getWeekday(Calendar.getInstance())

        // 변경이 감지되면 Repository에서 반환한 LiveData(lectures)를 MutableLiveData인 _lectures에 저장한다.
        repository.getLectures("MONDAY") { lectures ->
            _mondayLectures.value = lectures
        }
    }

    fun updateLecturesForTuesday() {
        repository.getLectures("TUESDAY") { lectures ->
            _tuesdayLectures.value = lectures
        }
    }

    fun updateLecturesForWednesday() {
        repository.getLectures("WEDNESDAY") { lectures ->
            _wednesdayLectures.value = lectures
        }
    }

    fun updateLecturesForThursday() {
        repository.getLectures("THURSDAY") { lectures ->
            _thursdayLectures.value = lectures
        }
    }

    fun updateLecturesForFriday() {
        repository.getLectures("FRIDAY") { lectures ->
            _fridayLectures.value = lectures
        }
    }

    fun getMondayLectures(): LiveData<List<Lecture>> {
        return mondayLectures
    }

    fun getTuesdayLectures(): LiveData<List<Lecture>> {
        return tuesdayLectures
    }

    fun getWednesdayLectures(): LiveData<List<Lecture>> {
        return wednesdayLectures
    }

    fun getThursdayLectures(): LiveData<List<Lecture>> {
        return thursdayLectures
    }

    fun getFridayLectures(): LiveData<List<Lecture>> {
        return fridayLectures
    }
}


