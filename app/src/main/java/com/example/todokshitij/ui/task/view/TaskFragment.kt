package com.example.todokshitij.ui.task.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.todokshitij.R
import com.example.todokshitij.databinding.FragmentTaskBinding
import com.example.todokshitij.ui.task.model.Task
import com.example.todokshitij.utils.Constants.TASK_DETAILS
import com.example.todokshitij.utils.Constants.TASK_POSITION
import java.text.SimpleDateFormat
import java.util.*

class TaskFragment(private val addTaskListener: AddTaskListener) : Fragment() {

    private var binding: FragmentTaskBinding? = null

    private var isEditing = false

    private var editingPos: Int? = null

    companion object {
        var DATE_FORMAT_PATTERN = "dd/MM/yyyy ',' HH:mm"
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)

        checkIsEditing()
        formatDate()
        setupOnClickListeners()

        return binding?.root
    }

    private fun checkIsEditing() {

        if (arguments != null) {

            isEditing = true
            val task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable(TASK_DETAILS, Task::class.java)
            } else {
                arguments?.getParcelable(TASK_DETAILS)
            }
            editingPos = arguments?.getInt(TASK_POSITION)

            binding?.apply {
                editTextTitle.editText?.setText(task?.title)
                editTextDesc.editText?.setText(task?.description)
                textViewTime.text = task?.createdTime
                textViewSchedule.text = task?.scheduleTime
            }
        }
    }

    private fun formatDate() {
        val createdAt = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault()).format(Date())
        binding?.textViewTime?.text = getString(R.string.created_time, createdAt)
    }

    private fun setupOnClickListeners() {

        binding?.textViewSchedule?.setOnClickListener {
            showDateTimePicker()
        }

        binding?.tickButton?.setOnClickListener {

            (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                binding?.tickButton?.windowToken, 0
            )
            val title = binding?.editTextTitle?.editText?.text.toString()
            val desc = binding?.editTextDesc?.editText?.text.toString()
            val scheduleTime = binding?.textViewSchedule?.text.toString()
            val createdTime = binding?.textViewTime?.text.toString()

            if (isEditing) {
                addTaskListener.onEditTask(
                    task = Task(title, desc, createdTime, scheduleTime),
                    editingPos!!
                )
            } else {
                addTaskListener.onAddTask(task = Task(title, desc, createdTime, scheduleTime))
            }
        }
    }

    private fun showDateTimePicker() {
        val currentDate = Calendar.getInstance()
        val date = Calendar.getInstance()
        DatePickerDialog(
            requireContext(), { _, year, monthOfYear, dayOfMonth ->
                date.set(year, monthOfYear, dayOfMonth)
                TimePickerDialog(
                    context, { _, hourOfDay, minute ->
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        date.set(Calendar.MINUTE, minute)

                        binding?.textViewSchedule?.text = getString(
                            R.string.scheduled_time,
                            dayOfMonth.toString(),
                            monthOfYear.toString(),
                            year.toString(),
                            hourOfDay.toString(),
                            minute.toString()
                        )

                    }, currentDate[Calendar.HOUR_OF_DAY], currentDate[Calendar.MINUTE], false
                ).show()
            }, currentDate[Calendar.YEAR], currentDate[Calendar.MONTH], currentDate[Calendar.DATE]
        ).show()
    }

    interface AddTaskListener {
        fun onAddTask(task: Task)
        fun onEditTask(task: Task, position: Int)
    }
}