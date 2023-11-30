package com.example.mymultifragapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mymultifragapplication.databinding.FragmentMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.*

class MainFragment : Fragment() {

    var binding: FragmentMainBinding? = null

    /*private var className: String? = null
    private var classProfessor: String? = null

    private var classDay : String? = null
    private var timeStartH : String? = null
    private var timeStartM : String? = null
    private var timeEndH : String? = null
    private var timeEndM : String? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            className = it.getString("CLASS_NAME")
            classProfessor = it.getString("CLASS_PROFESSOR")
            classDay = it.getString("CLASS_DAY")
            timeStartH = it.getString("TIME_START_H")
            timeStartM = it.getString("TIME_START_M")
            timeEndH = it.getString("TIME_END_H")
            timeEndM = it.getString("TIME_END_M")
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding?.root // binding 최상위 view
    }

    // 위 onCreateView가 끝나고 실행
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //test
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val lectureRef: DatabaseReference = database.getReference("timetable")
        var name : String
        var startTimeHour: String
        var startTimeMin : String
        var endTimeHour : String
        var endTimeMin : String

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (daySnapshot in dataSnapshot.children) {
                        val day = daySnapshot.key // 각 요일 가져오기 (예: "MONDAY", "TUESDAY", 등)
                        // day에 해당하는 데이터를 가져와서 활용할 수 있습니다.
                        for (lectureSnapshot in daySnapshot.children) {
                            // 각 강의 정보에 접근할 수 있습니다. 예: lectureSnapshot.child("name").value.toString()
                            name = lectureSnapshot.child("name").value.toString()
                            startTimeHour = lectureSnapshot.child("startTimeHour").value.toString()
                            startTimeMin = lectureSnapshot.child("startTimeMin").value.toString()
                            endTimeHour = lectureSnapshot.child("endTimeHour").value.toString()
                            endTimeMin = lectureSnapshot.child("endTimeMin").value.toString()
                        }
                    }
                } else {
                    // 데이터가 없을 때 처리할 내용
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터를 가져오는 데 문제가 발생했을 때 처리할 내용
            }
        }


        if (startTimeHour < 9) {
            startTimeHour = 9
            startTimeHour = "09"
        }

        if (endTimeHour >= 22 && endTimeMin > 0) {
            endTimeHour = 22
            endTimeHour = "22"
            endTimeMin = 0
            endTimeMin = "00"
        }

        if (startTimeHour == startTimeMin && endTimeHour == endTimeMin) {
        } else {
            val hour_subtract = (endTimeHour - startTimeHour) * 2
            val minute_subtract = (endTimeMin - startTimeMin) / 30
            val result = hour_subtract + minute_subtract
            val row = (startTimeHour - 9) * 2 + (if (startTimeMin == 30) 2 else 1)

            var temp = 0
            for (j in 0 until result - 1) {
                val delete_cell_hour = if (startTimeMin == 30) {
                    if (startTimeHour + 1 < 10) {
                        "0" + (startTimeHour + 1).toString()
                    } else {
                        (startTimeHour + 1).toString()
                    }
                } else {
                    if (startTimeHour < 10) {
                        "0" + startTimeHour.toString()
                    } else {
                        startTimeHour.toString()
                    }
                }
                startTimeHour = if (startTimeMin == 30) startTimeHour + 1 else startTimeHour
                val delete_cell_minute = if (startTimeMin == 30) "00" else "30"
                startTimeMin = if (startTimeMin == 30) 0 else 30
                val delete_cell_name = dayToEnglish(day) + delete_cell_hour + delete_cell_minute
                val deleteCell = view.findViewWithTag<TextView>(delete_cell_name)
                val gridLayout2: GridLayout = view.findViewById(R.id.gridlayout_timetable)
                gridLayout2.removeView(deleteCell)
            }
        }

        private fun getSpanCellID(day: String, beforeHour: Int, beforeMinute: Int): String {
            return day.toEnglish() + beforeHour.toString() + beforeMinute.toString()
        }

        fun setSpanCellLayout(view: View, day: String, row: Int, result: Int) {
            val spanCellID = getSpanCellID(day, result, row)
            val spanCell = view.findViewWithTag(spanCellID) as TextView
            val layoutParams = spanCell.layoutParams as GridLayout.LayoutParams
            layoutParams.columnSpec = GridLayout.spec(day.toNum())
            layoutParams.rowSpec = GridLayout.spec(row, result)
            spanCell.layoutParams = layoutParams
            layoutParams.gravity = Gravity.FILL
            spanCell.layoutParams = layoutParams
            spanCell.text = " "

            // Set background
            spanCell.setBackgroundDrawable(resources.getDrawable(R.drawable.fill_cell))
        }




        fun dayToNum(day: String): Int {
            return when (day.lowercase()) {
                "monday" -> 1
                "tuesday" -> 2
                "wednesday" -> 3
                "thursday" -> 4
                "friday" -> 5
                else -> 0
            }
        }



        //test

        binding?.btnRegister?.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_registerFragment)
        }

        binding?.btnSemester?.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_semesterFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}