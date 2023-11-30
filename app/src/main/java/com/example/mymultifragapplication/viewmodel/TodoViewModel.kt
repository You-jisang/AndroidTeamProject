package com.example.mymultifragapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymultifragapplication.repository.Todo
import com.example.mymultifragapplication.repository.TodoRepository

class TodoViewModel(private val db: TodoRepository = TodoRepository()) : ViewModel() {
    val allTasks: LiveData<List<Todo>> = db.getAllTasks()

    fun addTask(todo: Todo) = db.addTask(todo)

    fun updateStatus(id: Long, isChecked: Boolean) = db.updateStatus(id, isChecked)

    fun updateTodo(todo: Todo) = db.updateTodo(todo)

    fun deleteTasks(ids: List<Long>) {
        if (ids.isEmpty()) return

        db.deleteTask(ids[0]).addOnCompleteListener {
            deleteTasks(ids.drop(1))
        }
    }

    fun getTask(id: Long): LiveData<Todo> {
        return db.getTask(id)
    }
}