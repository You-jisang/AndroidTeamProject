package com.example.mymultifragapplication.repository

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

    fun getLectures(weekday: String, callback: (List<Lecture>) -> Unit) {
        lectureRef.child(weekday).addValueEventListener(object : ValueEventListener {
            // 스냅샷 = 변경된 데이터
            override fun onDataChange(snapshot: DataSnapshot) {
                val lectures = snapshot.children.mapNotNull { it.getValue(Lecture::class.java) }
                callback(lectures)
            }

            // 데이터 읽기/쓰기 취소 되었을 때
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun saveLectureToFirebase(day: String, lecture: Lecture) {
        val addRef = lectureRef.child(day).push()
        addRef.setValue(lecture)
    }

}

