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
            binding.textLocation.text = lecture.location
            binding.textStart.text = "${lecture.startTime}"
            binding.textEnd.text = "${lecture.endTime}"

            // 과목에 따라 마커 이미지를 변경
            val markerImageResId = when (lecture.location) {
                "강의동" -> R.drawable.location_yellow
                "과학관" -> R.drawable.location_blue
                "전자관" -> R.drawable.location_red
                "기계관" -> R.drawable.location_green

                else -> R.drawable.location_yellow // 기본 마커 이미지
            }
            binding.imageView.setImageResource(markerImageResId)
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

