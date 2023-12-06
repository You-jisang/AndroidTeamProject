package com.example.mymultifragapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymultifragapplication.viewmodel.Todolist

class TodoCheckMenuAdapter(private val listener: TaskItemClickListener) :
    RecyclerView.Adapter<TodoCheckMenuAdapter.TodoViewHolder>() {
    private var tasks = emptyList<Todolist>()

    val checkedItems = mutableMapOf<Long, Todolist>()

    // ID, 항목 저장
    interface TaskItemClickListener {
        fun onTaskItemChecked(id: Long, isChecked: Boolean)
    }

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val source: TextView = itemView.findViewById(R.id.Todosource)
        val timestamp: TextView = itemView.findViewById(R.id.TodoTimeStamp)
        val dDay: TextView = itemView.findViewById(R.id.TodoDday)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todolist, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentItem = tasks[position]

        // 체크 상태 변경 리스너 설정
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkedItems[currentItem.id!!] = currentItem
            } else {
                checkedItems.remove(currentItem.id!!)
            }

            listener.onTaskItemChecked(currentItem.id, isChecked)
        }

        holder.source.text = currentItem.title
        holder.timestamp.text = currentItem.timestamp.toString()
        holder.dDay.text = currentItem.dday.toString()

        // 체크박스 체크 여부 설정
        holder.checkBox.isChecked = currentItem.id?.let { checkedItems.containsKey(it) } ?: false
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newTasks: List<Todolist>) {
        tasks = newTasks
        notifyDataSetChanged()  // 데이터 변경을 알림
    }
}