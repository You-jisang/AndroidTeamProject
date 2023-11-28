package com.example.mymultifragapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymultifragapplication.databinding.FragmentEditlistBinding
import com.example.mymultifragapplication.repository.Todo
import com.example.mymultifragapplication.viewmodel.TodoViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class EditlistFragment : Fragment() {
    private lateinit var viewModel: TodoViewModel
    var binding: FragmentEditlistBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        binding = FragmentEditlistBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong("itemId", -1)

        if (id != null) {
            viewModel.getTask(id).observe(viewLifecycleOwner, Observer { todo ->
                binding?.etTodoTitle?.setText(todo?.title)
                binding?.etTodoTask?.setText(todo?.task)
            })
        }

        binding?.btnSave?.setOnClickListener {

            val title = binding?.etTodoTitle?.text.toString()
            val task = binding?.etTodoTask?.text.toString()
            val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())

            // D-day 구하기

            val dday = 0

            // 여기까지

            if (id == -1L) {
                // 추가
                val newId = System.currentTimeMillis()
                val todo = Todo(newId, title, task, timestamp, dday)
                viewModel.addTask(todo)
            } else {
                // 수정
                id?.let {
                    val updatedTodo =
                        Todo(it, title = title, task = task, timestamp = timestamp, dday = dday)
                    viewModel.updateTodo(updatedTodo)
                }
            }

            findNavController().navigate(R.id.action_editlistFragment_to_todolistFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
