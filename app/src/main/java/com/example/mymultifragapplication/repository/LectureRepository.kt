package com.example.mymultifragapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymultifragapplication.viewmodel.Lecture
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LectureRepository {
    // firebase realtime database에서 timetable 레퍼런스 가져오기
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val lectureRef: DatabaseReference = database.getReference("timetable")

    //Lecture 객체의 리스트 LiveData 반환
    fun getLectures(weekday: String): LiveData<List<Lecture>> {
        val lecturesLiveData = MutableLiveData<List<Lecture>>()
        //데이터의 변화를 감지할 시 콜백 함수 호출
        lectureRef.child(weekday).addValueEventListener(object : ValueEventListener {
            // 데이터가 변경되었을 때 호출
            // 스냅샷 = 단일 시점에 특정 데이터베이스 참조에 있던 데이터를 촬영한 사진
            override fun onDataChange(snapshot: DataSnapshot) {
                // 자식 노드를 Lecture의 인스턴스로 변환
                val lectures = snapshot.children.mapNotNull { it.getValue(Lecture::class.java) }
                // 인자로 받은 값을 LiveData로 설정, 옵저버에게 알림
                lecturesLiveData.postValue(lectures)
            }

            // 데이터 읽기/쓰기 취소 되었을 때
            override fun onCancelled(error: DatabaseError) {
            }
        })
        return lecturesLiveData
    }
}

