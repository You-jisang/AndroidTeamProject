package com.example.mymultifragapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymultifragapplication.viewmodel.Lecture
import com.google.firebase.database.*

class LectureRepository {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val lectureRef: DatabaseReference = database.getReference("timetable")

    fun getLectures(weekday: String): LiveData<List<Lecture>> {
        val lecturesLiveData = MutableLiveData<List<Lecture>>()
        lectureRef.child(weekday).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lectures = snapshot.children.mapNotNull { it.getValue(Lecture::class.java) }
                lecturesLiveData.postValue(lectures)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error here
            }
        })
        return lecturesLiveData
    }
}

