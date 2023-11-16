package com.example.mymultifragapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymultifragapplication.databinding.FragmentScheduleAddBinding
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mymultifragapplication.databinding.FragmentMapBinding
import com.example.mymultifragapplication.viewmodel.DateViewModel


// import com.example.mymultifragapplication.viewmodel.ScheduleAddViewModel

class RegisterFragment : Fragment() {

    var binding : FragmentScheduleAddBinding? = null
    // private lateinit var scheduleAddViewModel: ScheduleAddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleAddBinding.inflate(inflater)
        return binding?.root
    }

    private fun saveDataToBundle(): Bundle {
        val className = binding?.className?.text.toString()
        val classProfessor = binding?.classProfessor?.text.toString()
        val classBuilding = binding?.classBuilding?.text.toString()
        val classLocation = binding?.classLocation?.text.toString()
        val classDay = binding?.classDay?.text.toString()
        val timeStartH = binding?.timeStartH?.text.toString()
        val timeStartM = binding?.timeStartM?.text.toString()
        val timeEndH = binding?.timeEndH?.text.toString()
        val timeEndM = binding?.timeEndM?.text.toString()

        val bundle = Bundle().apply {
            putString("CLASS_NAME", className)
            putString("CLASS_PROFESSOR", classProfessor)
            putString("CLASS_LOCATION", classLocation)
            putString("CLASS_BUILDING", classBuilding)
            putString("CLASS_DAY", classDay)
            putString("TIME_START_H", timeStartH)
            putString("TIME_START_M", timeStartM)
            putString("TIME_END_H", timeEndH)
            putString("TIME_END_M", timeEndM)
        }
        return bundle
    }

    private fun onClickConfirmButton() {
        val bundle = saveDataToBundle()
        val navController = findNavController()
        navController.navigate(R.id.action_registerFragment_to_mainFragment, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.addDay?.setOnClickListener {

        }

        binding?.btnScheduleAddConfirm?.setOnClickListener {
            onClickConfirmButton()
        }
        binding?.btnScheduleAddCancel?.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        //scheduleAddViewModel = ViewModelProvider(requireActivity()).get(ScheduleAddViewModel::class.java)

        /*btnScheduleAddConfirm.setOnClickListener {
            val className = editTextClassName.text.toString()
            val classProfessor = editTextClassProfessor.text.toString()
            val classDay = editTextClassDay.text.toString()
            val timeStartH = editTextTimeStartH.text.toInt()
            val timeStartM = editTextTimeStartM.text.toInt()
            val timeEndH = editTextTimeEndH.text.toInt()
            val timeEndM = editTextTimeEndM.text.toInt()

            val dataList = listOf(
                className,
                classProfessor
                classLocation
                classDay
                timeStartH
                timeStartM
                timeEndH
                timeEndM
            )
            scheduleAddViewModel.setInputData(dataList)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}