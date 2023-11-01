package com.example.mymultifragapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mymultifragapplication.databinding.FragmentMapBinding
import com.example.mymultifragapplication.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    var binding: FragmentRegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding?.root
    }

}