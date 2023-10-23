package com.example.mymultifragapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import com.example.mymultifragapplication.databinding.FragmentInputBinding


class InputFragment() : Fragment() {

    var text: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //대부분 작업
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentInputBinding.inflate(inflater, container, false)
        arguments?.let {
            binding.editText.setText(it.getString("text"))
        }

        binding.editText.addTextChangedListener {
            val resBundle = Bundle().apply {
                putString("input", binding.editText.text.toString())
            }
            setFragmentResult("input_text", resBundle)


        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(str: String? = null) =
            InputFragment().apply {
                arguments = Bundle().apply {
                    str?.let {
                        putString("text", it)
                    }
                }
            }
    }

    /*
        @JvmStatic
        fun newInstance(str: String) =
            InputFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
            */

}



