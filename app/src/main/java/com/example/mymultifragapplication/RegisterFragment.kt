package com.example.mymultifragapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mymultifragapplication.databinding.FragmentRegisterBinding
import com.example.mymultifragapplication.viewmodel.ScheduleAddViewModel

enum class DayOfWeek(val korean: String, val english: String) {
    MONDAY("월", "MONDAY"),
    TUESDAY("화", "TUESDAY"),
    WEDNESDAY("수", "WEDNESDAY"),
    THURSDAY("목", "THURSDAY"),
    FRIDAY("금", "FRIDAY")
}

class RegisterFragment : Fragment() {
    private val viewModel: ScheduleAddViewModel by activityViewModels()
    private var binding: FragmentRegisterBinding? = null
    private lateinit var location: String
    private lateinit var day: DayOfWeek

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLocationRadioListener()
        setupDayRadioListener()

        binding?.btnScheduleAddConfirm?.setOnClickListener {
            saveScheduleData()
        }

        binding?.btnScheduleAddCancel?.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }
    }

    private fun setupLocationRadioListener() {
        binding?.locationRadio?.setOnCheckedChangeListener { _, checkedIdL ->
            location = when (checkedIdL) {
                R.id.check_1 -> binding?.check1?.text.toString()
                R.id.check_2 -> binding?.check2?.text.toString()
                R.id.check_3 -> binding?.check3?.text.toString()
                R.id.check_4 -> binding?.check4?.text.toString()
                else -> ""
            }
        }
    }

    private fun setupDayRadioListener() {
        binding?.dayRadio?.setOnCheckedChangeListener { _, checkedIdD ->
            day = when (checkedIdD) {
                R.id.check_MONDAY -> DayOfWeek.MONDAY
                R.id.check_TUESDAY -> DayOfWeek.TUESDAY
                R.id.check_WEDNESDAY -> DayOfWeek.WEDNESDAY
                R.id.check_THURSDAY -> DayOfWeek.THURSDAY
                R.id.check_FRIDAY -> DayOfWeek.FRIDAY
                else -> DayOfWeek.MONDAY // 기본값으로 월요일 설정
            }
        }
    }

    private fun saveScheduleData() {
        val name = binding?.className?.text.toString()
        val professor = binding?.classProfessor?.text.toString()
        val locationNum = binding?.classLocationNum?.text.toString()
        val startTimeHour = binding?.timeStartH?.text.toString()
        val startTimeMin = binding?.timeStartM?.text.toString()
        val endTimeHour = binding?.timeEndH?.text.toString()
        val endTimeMin = binding?.timeEndM?.text.toString()

        viewModel.saveDataToFirebase(
            day.english,
            name,
            location,
            locationNum,
            startTimeHour,
            startTimeMin,
            endTimeHour,
            endTimeMin
        )

        findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
