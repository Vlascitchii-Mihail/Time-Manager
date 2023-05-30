package com.vm.timemanager.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vm.timemanager.data.DatabaseTimeManager
import com.vm.timemanager.data.RepositoryTimeManager
import com.vm.timemanager.data.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewTaskAddingViewModel(application: Application): AndroidViewModel(application) {
    private val repository: RepositoryTimeManager

    var task: MutableLiveData<Task> = MutableLiveData<Task>(Task())
    private val _taskStateFlow: MutableStateFlow<Task> = MutableStateFlow(Task())
    val taskFlow: StateFlow<Task>
        get() = _taskStateFlow.asStateFlow()

    fun getNewTask() {
        _taskStateFlow.value = Task()
    }

    var day: String? = null

    init {
        repository = DatabaseTimeManager.getDatabase(application)
            .getDaoTimeManager().let { dao->
                RepositoryTimeManager.getRepository(dao)
            }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.addTask(task)
        }
    }

    fun getTaskFlow(taskId: Int) {
        viewModelScope.launch {
//            withContext(Dispatchers.Main) {
                repository.getTaskFlow(taskId).collect {
                    _taskStateFlow.value = it
                }
//            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            this@NewTaskAddingViewModel.taskFlow.value.let {
                repository.deleteTask(it)
            }
        }
    }
}