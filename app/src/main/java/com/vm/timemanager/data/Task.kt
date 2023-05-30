package com.vm.timemanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey (autoGenerate = true) var id: Int = 0,
    val day: String? = null,
    var taskName: String = "",
    var startTime: LocalDateTime? = null,
    var taskDescription: String = ""
)
