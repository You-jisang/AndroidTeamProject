package com.example.mymultifragapplication.repository

import androidx.lifecycle.MutableLiveData
import com.example.mymultifragapplication.viewmodel.Todolist
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TodoRepository {
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("todo_list")

    fun addTask(todo: Todolist) = todo.id?.let { database.child(it.toString()).setValue(todo) }

    fun updateStatus(id: Long, isChecked: Boolean) =
        database.child(id.toString()).child("isChecked").setValue(isChecked)


    fun updateTodo(todo: Todolist) = todo.id?.let { database.child(it.toString()).setValue(todo) }

    fun deleteTask(id: Long): Task<Void> = database.child(id.toString()).removeValue()

    // id를 기준으로 모든 항목을 가져옴
    fun getAllTasks(editTasks: MutableLiveData<List<Todolist>>) {
        database.orderByChild("id").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tasks = snapshot.children.mapNotNull { it.getValue(Todolist::class.java) }
                editTasks.postValue(tasks)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getTask(id: Long, task: MutableLiveData<Todolist>) {
        database.child(id.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = snapshot.getValue(Todolist::class.java)
                task.postValue(result)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}