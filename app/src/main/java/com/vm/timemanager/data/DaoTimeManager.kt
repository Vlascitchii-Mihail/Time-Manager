package com.vm.timemanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DaoTimeManager {
    @Insert
    abstract suspend fun addTask(task: Task)

    @Update
    abstract suspend fun updateTask(task: Task)

    @Delete
    abstract suspend fun deleteTask(task: Task)

    //the Room has already use background threads for returning LiveData
    @Query("SELECT * FROM tasks WHERE day = :taskDay ORDER BY id DESC")
    abstract fun getAllTasks(taskDay: String): LiveData<List<Task>>

    //the Room has already use background threads for returning LiveData
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    abstract suspend fun getTask(taskId: Int): Task

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    abstract fun getTaskFlow(taskId: Int): Flow<Task>

}