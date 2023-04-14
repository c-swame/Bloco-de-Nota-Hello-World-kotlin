package com.example.helloworld

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CardLayoutAdapter.ViewHolder>? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)

        layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        adapter = CardLayoutAdapter(getTasksFromSharedPreferences())
        binding.recyclerView.adapter = adapter

        binding.btnCreateTask.setOnClickListener {
            val newTask = CardElementModel("Nova tarefa", "Nova descrição")
            (adapter as CardLayoutAdapter).tasksToDo?.add(newTask)
            saveTaskToSharedPreferences(newTask)
            (adapter as CardLayoutAdapter).notifyDataSetChanged()
        }
    }

    private fun getTasksFromSharedPreferences(): MutableList<CardElementModel>? {
        val tasksJson = sharedPreferences.getString("tasks", "[]")
        val tasksType = object : TypeToken<MutableList<CardElementModel>>() {}.type
        return Gson().fromJson(tasksJson, tasksType)
    }

    private fun saveTaskToSharedPreferences(task: CardElementModel) {
        val tasks = getTasksFromSharedPreferences()
        if (tasks != null) {
            tasks.add(task)
        }
        val editor = sharedPreferences.edit()
        editor.putString("tasks", Gson().toJson(tasks))
        editor.apply()
    }
}
