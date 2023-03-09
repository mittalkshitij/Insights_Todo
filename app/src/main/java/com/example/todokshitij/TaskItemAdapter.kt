package com.example.todokshitij

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todokshitij.databinding.ItemLayoutBinding

class TaskItemAdapter(private var taskList : ArrayList<Task>) :
    RecyclerView.Adapter<TaskItemAdapter.MyViewHolder>(){

    class MyViewHolder (var binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindingView(task: Task){

            binding.tVItemTitle.text = task.title
            binding.tVItemCreatedAt.text = task.createdAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bindingView(taskList[position])
    }

    interface RemoveTaskListener{
        fun removeTask(task: Task)
    }
}