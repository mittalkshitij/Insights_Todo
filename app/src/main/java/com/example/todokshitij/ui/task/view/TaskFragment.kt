package com.example.todokshitij.ui.task.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
import com.example.todokshitij.utils.DateToString
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class TaskFragment() : Fragment() {

    private var binding: FragmentTaskBinding? = null

    private var task: Task? = null

    private val homeViewModel: HomeViewModel by viewModels()

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var calendar = Calendar.getInstance()

    companion object {
        private const val FINE_LOCATION_REQUEST = 100
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)

        checkIsEditing()
        binding?.textViewTime?.text = getString(R.string.created_at) + DateToString.formatDate(Calendar.getInstance().time)
        setupOnClickListeners()

        return binding?.root
    }

    @Suppress("DEPRECATION")
    private fun checkIsEditing() {

        task = arguments?.getParcelable(TASK_DETAILS)

        if (task != null) {

            binding?.apply {

                editTextTitle.editText?.setText(task?.title)
                editTextDesc.editText?.setText(task?.description)
                textViewTime.text = task?.createdTime.toString()
                textViewSchedule.text = task?.scheduleTime.toString()
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
                val scheduleTime = calendar.time
                val createdTime = Calendar.getInstance().time


                if (title.isEmpty() ){
                    Toast.makeText(requireContext(),"Title cannot be empty.",Toast.LENGTH_SHORT).show()
                }else {

                    val task = Task(id, title, desc, createdTime,scheduleTime)
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
    }

    private fun checkPermissions() {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

            fusedLocationClient?.lastLocation?.addOnSuccessListener { location: Location? ->

                binding?.textViewLocation?.text = getString(
                    R.string.getLocation,
                    location?.latitude.toString(),
                    location?.longitude.toString()
                )
                return@addOnSuccessListener
            }
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            FINE_LOCATION_REQUEST
        )
    }

    private fun showDateTimePicker() {

        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
        datePicker.addOnPositiveButtonClickListener {

            calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            timePicker.show(childFragmentManager, "TAG")
        }

        timePicker.addOnPositiveButtonClickListener{

            calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            calendar.set(Calendar.MINUTE, timePicker.minute)
            calendar.set(Calendar.SECOND, 5)

            binding?.textViewSchedule?.text =  DateToString.formatDate(calendar.time)
        }
        datePicker.show(childFragmentManager,"TAG")
    }
}