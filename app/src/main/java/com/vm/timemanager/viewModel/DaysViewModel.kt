package com.vm.timemanager.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.vm.timemanager.data.DatabaseTimeManager
import com.vm.timemanager.data.RepositoryTimeManager
import com.vm.timemanager.data.Task

class DaysViewModel(application: Application): AndroidViewModel(application) {

    private val repository: RepositoryTimeManager
    lateinit  var allTasks: LiveData<List<Task>>
    var dayName: String = ""

    init {
        repository = DatabaseTimeManager.getDatabase(application)
            .getDaoTimeManager().let { dao->
                RepositoryTimeManager.getRepository(dao)
            }
    }

    fun getAllTasks() {
        allTasks = repository.getAllTasks(dayName)
    }

}