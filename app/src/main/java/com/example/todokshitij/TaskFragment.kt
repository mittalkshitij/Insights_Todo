package com.example.todokshitij

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.todokshitij.databinding.FragmentTaskBinding
import java.text.SimpleDateFormat
import java.util.*

class TaskFragment(private val addTaskListener: AddTaskListener) : Fragment() {

    private var binding: FragmentTaskBinding? = null

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)

        val sdf = SimpleDateFormat("yyyy/MM/dd ',' HH:mm")
        val createdAt: String = sdf.format(Date())
        binding?.textViewTime?.text = "Created at $createdAt"

        binding?.tickButton?.setOnClickListener {
            (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                binding?.tickButton?.windowToken,
                0
            )

            val title = binding?.editTextTitle?.editText?.text.toString()
            val desc = binding?.editTextDesc?.editText?.text.toString()

            addTaskListener.onAddTask(task = Task(title, desc, createdAt))
        }
        return binding?.root
    }

    interface AddTaskListener {
        fun onAddTask(task: Task)
    }
}