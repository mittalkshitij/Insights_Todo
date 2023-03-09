package com.example.todokshitij

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.todokshitij.databinding.FragmentTaskBinding

class TaskFragment(private val addTaskListener: AddTaskListener) : Fragment() {

    private var binding: FragmentTaskBinding? = null

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)


        binding?.tickButton?.setOnClickListener {

            val title = binding?.editTextTitle?.editText?.text.toString()
            val desc = binding?.editTextDesc?.editText?.text.toString()
            val createdAt = binding?.textViewTime?.text.toString()

            addTaskListener.onAddTask(task = Task(title,desc,createdAt))
        }
        return binding?.root
    }

    interface AddTaskListener{
        fun onAddTask(task: Task)
    }
}