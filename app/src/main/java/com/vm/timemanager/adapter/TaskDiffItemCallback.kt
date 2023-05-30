package com.vm.timemanager.adapter

import androidx.recyclerview.widget.DiffUtil
import com.vm.timemanager.data.Task

class TaskDiffItemCallback: DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) =
        (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Task, newItem: Task) =
        (oldItem == newItem)
}