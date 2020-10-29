package br.com.fiap.roomdatabase.task

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.roomdatabase.service.getService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel: ViewModel() {

    val tasksLiveData = MutableLiveData<List<Task>>()
    val newTaskLiveData = MutableLiveData<Task>()

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val result: List<Task>? = getService()?.getTasks()
            tasksLiveData.postValue(result)
        }
    }


    fun newTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            getService()?.newTask(task)
            newTaskLiveData.postValue(task)
        }
    }

}