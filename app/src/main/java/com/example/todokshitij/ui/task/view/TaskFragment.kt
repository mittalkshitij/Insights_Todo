package com.example.todokshitij.ui.task.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.todokshitij.R
import com.example.todokshitij.databinding.FragmentTaskBinding
import com.example.todokshitij.ui.home.viewmodel.HomeViewModel
import com.example.todokshitij.ui.task.model.Task
import com.example.todokshitij.utils.Constants.TASK_DETAILS
import com.example.todokshitij.utils.formatDate
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class TaskFragment() : Fragment() {

    private var binding: FragmentTaskBinding? = null

    private var task: Task? = null

    private val homeViewModel: HomeViewModel by viewModels()

    companion object {
        private const val FINE_LOCATION_REQUEST = 100
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)

        checkIsEditing()
        binding?.textViewTime?.text = getString(R.string.created_at) + formatDate()
        setupOnClickListeners()

        return binding?.root
    }

    private fun checkIsEditing() {

        task = arguments?.getParcelable(TASK_DETAILS)

        if (task != null) {

            binding?.apply {

                editTextTitle.editText?.setText(task?.title)
                editTextDesc.editText?.setText(task?.description)
                textViewTime.text = task?.createdTime
                textViewSchedule.text = task?.scheduleTime
            }
        }
    }


    private fun setupOnClickListeners() {

        binding?.apply {

            textViewSchedule.setOnClickListener {
                showDateTimePicker()
            }

            textViewLocation.setOnClickListener {
                checkPermissions()
            }

            tickButton.setOnClickListener {

                (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    tickButton.windowToken, 0
                )

                val id = if (task != null) task?.id else null
                val title = editTextTitle.editText?.text.toString()
                val desc = editTextDesc.editText?.text.toString()
                val scheduleTime = textViewSchedule.text.toString()
                val createdTime = textViewTime.text.toString()

                val task = Task(id, title, desc, createdTime, scheduleTime)

                if (id != null) {
                    lifecycle.coroutineScope.launch {
                        homeViewModel.updateTask(task)
                        parentFragmentManager.popBackStack()
                    }
                } else {
                    lifecycle.coroutineScope.launch {
                        homeViewModel.insertTask(task)
                        parentFragmentManager.popBackStack()
                    }
                }
            }
        }
    }

    private fun checkPermissions(): Boolean {

        return if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            requestPermission()
            false
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            FINE_LOCATION_REQUEST
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>, grantResults: IntArray
    ) {
        @Suppress("DEPRECATION")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == FINE_LOCATION_REQUEST) {

            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initLocationUpdate()
            } else {
                requestPermission()
            }
        }

    }

    private fun initLocationUpdate() {

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
}