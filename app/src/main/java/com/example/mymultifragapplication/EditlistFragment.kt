package com.example.mymultifragapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mymultifragapplication.databinding.FragmentEditlistBinding
import com.example.mymultifragapplication.databinding.FragmentRegisterBinding
import com.example.mymultifragapplication.databinding.FragmentTodolistBinding


class EditlistFragment : Fragment() {

    var binding : FragmentEditlistBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditlistBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSave?.setOnClickListener {
            findNavController().navigate(R.id.action_editlistFragment_to_todolistFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}