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


// import com.example.mymultifragapplication.viewmodel.ScheduleAddViewModel

class RegisterFragment : Fragment() {
    val viewModel: ScheduleAddViewModel by activityViewModels()
    var binding: FragmentRegisterBinding? = null
    private fun convertKoreanToEnglishDay(input: String): String {
        return when (input) {
            "월" -> "MONDAY"
            "화" -> "TUESDAY"
            "수" -> "WEDNESDAY"
            "목" -> "THURSDAY"
            "금" -> "FRIDAY"
            else -> "Invalid input" // 잘못된 입력 처리
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.btnScheduleAddConfirm?.setOnClickListener {
            val name = binding?.className?.text.toString()
            val professor = binding?.classProfessor?.text.toString()
            val location = binding?.classBuilding?.text.toString()
            val locationNum = binding?.classLocation?.text.toString()
            val day_k = binding?.classDay?.text.toString()
            val day = convertKoreanToEnglishDay(day_k)
            val startTimeHour = binding?.timeStartH?.text.toString()
            val startTimeMin = binding?.timeStartM?.text.toString()
            val endTimeHour = binding?.timeEndH?.text.toString()
            val endTimeMin = binding?.timeEndM?.text.toString()

            viewModel.saveDataToFirebase(
                day,
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
        binding?.btnScheduleAddCancel?.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}