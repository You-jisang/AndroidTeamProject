package com.example.mymultifragapplication.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymultifragapplication.repository.TodoRepository

class TodoViewModel(private val db: TodoRepository = TodoRepository()) : ViewModel() {
    val task: MutableLiveData<Todolist> = MutableLiveData()
    val editTasks: MutableLiveData<List<Todolist>> = MutableLiveData()
    init {
        db.getAllTasks(editTasks)
    }

    fun addTask(todo: Todolist) = db.addTask(todo)

    fun updateStatus(id: Long, isChecked: Boolean) = db.updateStatus(id, isChecked)

    fun updateTodo(todo: Todolist) = db.updateTodo(todo)

    fun deleteTasks(ids: List<Long>) {
        if (ids.isEmpty()) return

        db.deleteTask(ids[0]).addOnCompleteListener {
            deleteTasks(ids.drop(1))
        }
    }

    fun getTask(id: Long) {
        db.getTask(id, task)
    }
}
