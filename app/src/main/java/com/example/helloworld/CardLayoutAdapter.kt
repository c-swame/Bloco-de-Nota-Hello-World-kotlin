package com.example.helloworld

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.text.Editable
import android.text.TextWatcher

class CardLayoutAdapter(
    private val _taskToDo: MutableList<CardElementModel>?,
    val _sharedPreferences: SharedPreferences
    ): RecyclerView.Adapter<CardLayoutAdapter.ViewHolder>() {

    var tasksToDo: MutableList<CardElementModel>? = mutableListOf<CardElementModel>()
    var sharedPreferences: SharedPreferences

    init {
        tasksToDo = _taskToDo
        // !!??? Tentar colocar a lista de tarefas para serem inicializadas aqui e vêr se/como isso afeta a atualização da lista de tarefas;
        // !!! adicionar lista de tarefas realizadas;
        sharedPreferences = _sharedPreferences
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

        private fun getTasksFromSharedPreferences(): MutableList<CardElementModel>? {
            val tasksJson = sharedPreferences.getString("tasks", "[]")
            val tasksType = object : TypeToken<MutableList<CardElementModel>>() {}.type
            return Gson().fromJson(tasksJson, tasksType)
        }

        private fun saveTaskToSharedPreferences(task: CardElementModel) {
            /*
            !!! A lógica adicionada ao afterTextChanged deve ser otimizada e traziapa para essa função, de forma que qualquer alteração em um elemento o faça ser adicionado aqui
                Antes disso as taks deverão passar a ter um id girado pela ferramenta nativa de UUID. Aproveitar e adicionar a opção de cor;
                Caso na lista de tarefas não seja encontrado um elemento com o id da task a ser salva, só adicionar; caso contrario, remover para só depois adicionar (ou editar diretamente, se possível)
            */
            val tasks = getTasksFromSharedPreferences()
            val originalTask = tasks?.firstOrNull { it.taskId == task.taskId }

            if (tasks != null && originalTask == null) {
                tasks.add(task)
            } else if (originalTask != null){
                tasks?.firstOrNull { it.taskId == task.taskId }?.description = task.description
                tasks?.firstOrNull { it.taskId == task.taskId }?.title = task.title
            }
            val editor = sharedPreferences.edit()
            editor.putString("tasks", Gson().toJson(tasks))
            editor.apply()
        }

        init {
            itemTitle = itemView.findViewById(R.id.txt_setting_task_tittle)
            itemDescription = itemView.findViewById(R.id.txt_setting_task_content)

            itemTitle.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    tasksToDo?.get(adapterPosition)?.title = s.toString()
                    saveTaskToSharedPreferences(tasksToDo?.get(adapterPosition)!!)
                }
            })

            itemDescription.addTextChangedListener(object : TextWatcher {
                var initialDescription: String? = ""

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    initialDescription = s.toString()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    tasksToDo?.get(adapterPosition)?.description = s.toString()
                    saveTaskToSharedPreferences(tasksToDo?.get(adapterPosition)!!)
                }
            })
        }
    }
}