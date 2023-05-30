package com.vm.timemanager.data

import kotlinx.coroutines.flow.Flow

class RepositoryTimeManager(private val daoTimeManager: DaoTimeManager) {

    suspend fun addTask(task: Task) {
//        withContext(Dispatchers.Default) {
            daoTimeManager.addTask(task)
//        }
    }

    suspend fun updateTask(task: Task) {
//        withContext(Dispatchers.Default){
            daoTimeManager.updateTask(task)
//        }
    }

    suspend fun deleteTask(task: Task) {
//        withContext(Dispatchers.Default) {
            daoTimeManager.deleteTask(task)
//        }
    }

    fun getAllTasks(taskDay: String) = daoTimeManager.getAllTasks(taskDay)

    suspend fun getTask(taskId: Int): Task {
//        val task: Task
//        withContext(Dispatchers.Default) {
            return  daoTimeManager.getTask(taskId)
//        }
//        return task
    }

    fun getTaskFlow(taskId: Int): Flow<Task> {
//        val task: Task
//        withContext(Dispatchers.Default) {
        return  daoTimeManager.getTaskFlow(taskId)
//        }
//        return task
    }

    companion object {

        //Помечает вспомогательное поле JVM аннотированного свойства как изменчивое,
        // что означает, что записи в это поле немедленно становятся видимыми для других потоков.
        @Volatile
        private var instance: RepositoryTimeManager? = null

        /**
         * crete a new Repository's object
         */
        fun getRepository(dao: DaoTimeManager) =
            this.instance ?: synchronized(this) {
                instance ?: RepositoryTimeManager(dao).also {
                    instance = it
                }
            }
    }
}