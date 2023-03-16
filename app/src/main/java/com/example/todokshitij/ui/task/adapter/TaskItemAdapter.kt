package com.example.todokshitij.ui.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todokshitij.databinding.ItemLayoutBinding
import com.example.todokshitij.ui.task.model.Task

class TaskItemAdapter(
    private var taskList: ArrayList<Task>,
    private val removeTaskListener: RemoveTaskListener,
    private val taskClickListener: TaskClickListener
) :
    RecyclerView.Adapter<TaskItemAdapter.MyViewHolder>() {

    class MyViewHolder(private var binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindingView(
            task: Task,
            removeTaskListener: RemoveTaskListener,
            taskClickListener: TaskClickListener
        ) {

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
                taskClickListener.onTaskClick(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bindingView(taskList[position], removeTaskListener, taskClickListener)
    }

    interface RemoveTaskListener {
        fun removeTask(task: Task)
    }

    interface TaskClickListener {
        fun onTaskClick(taskPosition: Int)
    }
}