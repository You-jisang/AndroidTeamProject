package com.example.mymultifragapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymultifragapplication.databinding.FragmentTodolistBinding
import com.example.mymultifragapplication.viewmodel.TodoViewModel

@Suppress("DEPRECATION")
class TodolistFragment : Fragment(), TodoCheckMenuAdapter.TaskItemClickListener {
    private lateinit var viewModel: TodoViewModel
    private lateinit var todolistAdapter: TodoCheckMenuAdapter
    private lateinit var recyclerViewAdapter: TodoCheckMenuAdapter
    private var binding: FragmentTodolistBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)  // 이 Fragment에서 옵션 메뉴를 사용하도록 설정
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_option, menu)  // list_option 메뉴를 생성
        super.onCreateOptionsMenu(menu, inflater)
    }

    // 체크박스 선택 후 메뉴옵션
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // "선택항목 수정"메뉴
            R.id.menu_item_edit -> {
                if (todolistAdapter.checkedItems.size == 1) {
                    val itemToEditId = todolistAdapter.checkedItems.keys.first()
                    val action =
                        TodolistFragmentDirections.actionTodolistFragmentToEditlistFragment(itemToEditId)

                    findNavController().navigate(action)

                } else {
                    Toast.makeText(context, "하나의 항목만 수정할 수 있습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            // "선택항목 삭제"메뉴
            R.id.menu_item_delete -> {
                viewModel.deleteTasks(todolistAdapter.checkedItems.keys.toList())

                todolistAdapter.checkedItems.clear()
                Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        binding = FragmentTodolistBinding.inflate(inflater)
        return binding?.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = TodoCheckMenuAdapter(this)
        todolistAdapter = recyclerViewAdapter

        binding?.TodoList?.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = recyclerViewAdapter
        }

        binding?.fabAdd?.setOnClickListener {
            findNavController().navigate(R.id.action_todolistFragment_to_editlistFragment)
        }

        viewModel.editTasks.observe(viewLifecycleOwner) { tasks ->
            recyclerViewAdapter.setData(tasks)
            recyclerViewAdapter.notifyDataSetChanged()  // 데이터 변경을 알림
        }
    }

    override fun onTaskItemChecked(id: Long, isChecked: Boolean) {
        viewModel.updateStatus(id, isChecked)
    }
}
