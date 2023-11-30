package com.example.mymultifragapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymultifragapplication.databinding.ListLecturesBinding
import com.example.mymultifragapplication.viewmodel.Lecture

class LectureAdapter(private var lectures: List<Lecture>) :
    RecyclerView.Adapter<LectureAdapter.LectureViewHolder>() {

    inner class LectureViewHolder(private val binding: ListLecturesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(lecture: Lecture) {
            binding.textName.text = lecture.name
            binding.textLocationNum.text = lecture.locationNum
            binding.textStartHour.text = lecture.startTimeHour
            binding.textStartMin.text = lecture.startTimeMin
            binding.textEndHour.text = lecture.endTimeHour
            binding.textEndMin.text = lecture.endTimeMin
          
            // 과목에 따라 마커 이미지를 변경
            val markerImageId = when (lecture.location) {
                "강의동" -> R.drawable.location_yellow
                "과학관" -> R.drawable.location_blue
                "전자관" -> R.drawable.location_red
                "기계관" -> R.drawable.location_green

                else -> R.drawable.location_yellow // 기본 마커 이미지
            }
            binding.imageView.setImageResource(markerImageId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
        val binding =
            ListLecturesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LectureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LectureViewHolder, position: Int) {
        holder.bind(lectures[position])
    }

    override fun getItemCount(): Int = lectures.size
}

