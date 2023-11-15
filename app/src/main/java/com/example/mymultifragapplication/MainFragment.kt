package com.example.mymultifragapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mymultifragapplication.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    var binding: FragmentMainBinding? = null

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