package com.example.mymultifragapplication

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mymultifragapplication.databinding.FragmentMainBinding
import kotlin.random.Random
import androidx.fragment.app.activityViewModels
import com.example.mymultifragapplication.viewmodel.Lecture
import com.example.mymultifragapplication.viewmodel.ToTimetableViewModel


class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    private val viewModel: ToTimetableViewModel by activityViewModels()
    private val cellManager = CellManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeLectureObservables()
        setButtonClickListeners()
    }

    private fun initializeLectureObservables() {
        viewModel.apply {
            getMondayLectures().observe(viewLifecycleOwner) { lectures ->
                lectures.forEach { lecture ->
                    cellManager.fillCellsWithLectureInfo("monday", lecture)
                }
            }
            getTuesdayLectures().observe(viewLifecycleOwner) { lectures ->
                lectures.forEach { lecture ->
                    cellManager.fillCellsWithLectureInfo("tuesday", lecture)
                }
            }
            getWednesdayLectures().observe(viewLifecycleOwner) { lectures ->
                lectures.forEach { lecture ->
                    cellManager.fillCellsWithLectureInfo("wednesday", lecture)
                }
            }
            getThursdayLectures().observe(viewLifecycleOwner) { lectures ->
                lectures.forEach { lecture ->
                    cellManager.fillCellsWithLectureInfo("thursday", lecture)
                }
            }
            getFridayLectures().observe(viewLifecycleOwner) { lectures ->
                lectures.forEach { lecture ->
                    cellManager.fillCellsWithLectureInfo("friday", lecture)
                }
            }


        }
    }

    private fun setButtonClickListeners() {
        binding?.apply {
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_registerFragment)
            }
            btnSemester.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_semesterFragment)
            }
        }
    }

    inner class CellManager {
        fun fillCellsWithLectureInfo(day: String, lecture: Lecture) {
            val info = "${lecture.name}\n${lecture.location} ${lecture.locationNum}"
            fillCellsForTimeRange(day, lecture.startTimeHour.toInt(), lecture.startTimeMin.toInt(), lecture.endTimeHour.toInt(), lecture.endTimeMin.toInt(), info)
        }

        private fun fillCellsForTimeRange(day: String, startHour: Int, startMinute: Int, endHour: Int, endMinute: Int, info: String) {
            var currentHour = startHour
            var currentMinute = startMinute
            val colorRand = getRandomColor()

            while (currentHour < endHour || (currentHour == endHour && currentMinute <= endMinute)) {
                setCellBackgroundColor(day, currentHour, currentMinute, colorRand)

                currentMinute += 30
                if (currentMinute >= 60) {
                    currentHour++
                    currentMinute %= 60
                }
            }

            infoFirst(day, startHour, startMinute, info)
        }

        private fun setCellBackgroundColor(day: String, hour: Int, minute: Int, color: Int) {
            val cellName = "${day}${String.format("%02d", hour)}${String.format("%02d", minute)}"
            val cell = view?.findViewWithTag<TextView>(cellName)
            cell?.setBackgroundColor(color)
        }

        private fun infoFirst(day: String, hour: Int, minute: Int, text: String) {
            val cellName = "${day}${String.format("%02d", hour)}${String.format("%02d", minute)}"
            val cell = view?.findViewWithTag<TextView>(cellName)
            cell?.apply {
                this.text = text
                this.setTextColor(Color.WHITE)
            }
        }

        private fun getRandomColor(): Int {
            val r = Random.nextInt(256)
            val g = Random.nextInt(256)
            val b = Random.nextInt(256)
            return Color.rgb(r, g, b)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
