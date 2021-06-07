package com.bharathkalyans.todolist.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bharathkalyans.todolist.data.ToDoDatabase
import com.bharathkalyans.todolist.data.models.ToDoData
import com.bharathkalyans.todolist.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//View Model acts as a Communication Center bw UI and Repository!

class ToDoViewModel(application: Application) : AndroidViewModel(application) {


    //This is where Database is instantiated.
    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository: ToDoRepository

    private val getAllData: LiveData<List<ToDoData>>

    init {
        repository = ToDoRepository(toDoDao)
        getAllData = repository.getAllData
    }


    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }


}