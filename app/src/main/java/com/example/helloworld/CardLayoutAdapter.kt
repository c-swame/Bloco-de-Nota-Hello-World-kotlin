package com.example.helloworld

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardLayoutAdapter(
    private val _taskToDo: MutableList<CardElementModel>?,
    val _saveTaskToSharedPreferences: (task: CardElementModel) -> Unit
    ): RecyclerView.Adapter<CardLayoutAdapter.ViewHolder>() {
    var tasksToDo: MutableList<CardElementModel>? = mutableListOf<CardElementModel>()
    var saveTaskToSharedPreferences: (task: CardElementModel) -> Unit

    init {
        tasksToDo = _taskToDo
        saveTaskToSharedPreferences = _saveTaskToSharedPreferences
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardLayoutAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.task_card_layout, parent, false)
        return  ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CardLayoutAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = tasksToDo?.get(position)?.title ?: "Seu Título aqui"
        holder.itemDescription.text = tasksToDo?.get(position)?.description ?: ""
    }

    override fun getItemCount(): Int {
        return tasksToDo?.size ?: 0
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView
        var itemDescription: TextView

        init {
            itemTitle = itemView.findViewById(R.id.txt_setting_task_tittle)
            itemDescription = itemView.findViewById(R.id.txt_setting_task_content)
        }
    }
}