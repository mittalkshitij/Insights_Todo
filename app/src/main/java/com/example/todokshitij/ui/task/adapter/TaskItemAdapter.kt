package com.example.todokshitij.ui.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todokshitij.databinding.ItemLayoutBinding
import com.example.todokshitij.ui.home.viewmodel.HomeViewModel
import com.example.todokshitij.ui.task.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskItemAdapter(private val onTaskClick: (Task) -> Unit ,private val removeTaskListener: RemoveTaskListener) :
    ListAdapter<Task,TaskItemAdapter.MyViewHolder>(DiffUtilTask) {

    companion object {
        private val DiffUtilTask = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MyViewHolder(private var binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindingView(task : Task,removeTaskListener: RemoveTaskListener,onTaskClick: (Task) -> Unit) {

            binding.apply {
                tVItemTitle.text = task.title
                tVItemDescription.text = task.description
                tVItemCreatedAt.text = task.createdTime
                tVItemScheduleAt.text = task.scheduleTime
            }

            binding.imageViewDelete.setOnClickListener {
                removeTaskListener.removeTask(task)
            }

            binding.root.setOnClickListener {
                onTaskClick(task)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindingView(getItem(position),removeTaskListener,onTaskClick)
    }

    interface RemoveTaskListener{
         fun removeTask(task: Task)
    }

}