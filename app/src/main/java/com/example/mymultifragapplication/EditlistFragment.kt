package com.example.mymultifragapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymultifragapplication.databinding.FragmentEditlistBinding
import com.example.mymultifragapplication.viewmodel.TodoViewModel
import com.example.mymultifragapplication.viewmodel.Todolist
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EditlistFragment : Fragment() {
    private lateinit var viewModel: TodoViewModel
    private var binding: FragmentEditlistBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        binding = FragmentEditlistBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong("itemId", -1)

        // 날짜 선택 안하면 자동으로 오늘 날짜로 초기화
        var selectday: Long = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 1)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis


        // id 불러오기
        if (id != null) {
            viewModel.getTask(id = id)
            viewModel.task.observe(viewLifecycleOwner) { todo ->
                binding?.etTodoTitle?.setText(todo?.title)
                binding?.etTodoTask?.setText(todo?.task)
            }
        }


        binding?.ddayCalendar?.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
                // selectday와 todat 간 서로 다른 시간대에 있는 시간 차이를 없애기 위해 동일한 시간대 설정
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            selectday = selectedCalendar.timeInMillis
        }

        binding?.btnSave?.setOnClickListener {

            val title = binding?.etTodoTitle?.text.toString()
            val task = binding?.etTodoTask?.text.toString()
            val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())

            // val dday 값인 D-day 구하기
            val today = Calendar.getInstance().apply { // 현재 날짜
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            val dDayValue = (selectday - today) / (24 * 60 * 60 * 1000) // 밀리초를 날짜로 변환

            val dday = when {
                dDayValue < 0 -> "D+${-dDayValue}" // 선택한 날짜가 오늘 날짜 이전(과거)이면 D+Day로 표시
                dDayValue.toInt() == 0 -> "D-Day"
                else -> "D-${dDayValue}" // 선택한 날짜가 오늘 날짜 이후(미래)라면 D-Day로 표시
            }


            // 여기까지

            if (id == -1L) {
                // 추가
                val newId = System.currentTimeMillis()
                val todo = Todolist(newId, title, task, timestamp, dday)
                viewModel.addTask(todo)
            } else {
                // 수정
                id?.let {
                    val updatedTodo =
                        Todolist(it, title = title, task = task, timestamp = timestamp, dday = dday)
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
