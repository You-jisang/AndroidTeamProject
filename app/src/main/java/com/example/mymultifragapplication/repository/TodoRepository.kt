package com.example.mymultifragapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

data class Todo(
    val id: Long? = null,
    val title: String? = null,
    val task: String? = null,
    val timestamp: String? = null,
    val dday: String? = null
)

class TodoRepository {
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("todo_list")

    fun addTask(todo: Todo) = todo.id?.let { database.child(it.toString()).setValue(todo) }

    fun updateStatus(id: Long, isChecked: Boolean) =
        database.child(id.toString()).child("isChecked").setValue(isChecked)

    fun updateTask(id: Long, task: String) =
        database.child(id.toString()).child("task").setValue(task)

    fun updateTodo(todo: Todo) = todo.id?.let { database.child(it.toString()).setValue(todo) }

    fun deleteTask(id: Long): Task<Void> = database.child(id.toString()).removeValue()

    fun getAllTasks(): LiveData<List<Todo>> = MutableLiveData<List<Todo>>().apply {
        database.orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postValue(snapshot.children.mapNotNull { it.getValue(Todo::class.java) })
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun getTask(id: Long): LiveData<Todo> = MutableLiveData<Todo>().apply {
        database.child(id.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postValue(snapshot.getValue(Todo::class.java))
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}