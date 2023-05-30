package com.vm.timemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vm.timemanager.data.Task
import com.vm.timemanager.databinding.HolderTaskBinding

class AdapterDays(private val click: (taskId: Int) -> Unit): ListAdapter<Task, AdapterDays.TaskItemViewHolder>(TaskDiffItemCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder =
        TaskItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, click)
    }

    class TaskItemViewHolder(private val binding: HolderTaskBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Task, click: (taskId: Int) -> Unit) {
            binding.apply {
                task = item

                //executePendingBindings() -  используется, чтобы биндинг не откладывался,
                // а выполнился как можно быстрее. Это критично в случае с RecyclerView.
                executePendingBindings()

                root.setOnClickListener { click(item.id) }
            }
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HolderTaskBinding.inflate(layoutInflater, parent, false)
                return TaskItemViewHolder(binding)
            }

        }
    }
}