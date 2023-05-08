package com.example.todokshitij.ui.task.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import com.example.todokshitij.br.AlarmReceiver
import com.example.todokshitij.databinding.FragmentTaskBinding
import com.example.todokshitij.ui.home.view.HomeActivity
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
class TaskFragment : Fragment() {

    private var binding: FragmentTaskBinding? = null

    private var task: Task? = null

    private val homeViewModel: HomeViewModel by viewModels()

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private val calendar = Calendar.getInstance()

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
        setupOnClickListeners()
        createNotificationChannel()

        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    @Suppress("DEPRECATION")
    private fun checkIsEditing() {

        task = arguments?.getParcelable(TASK_DETAILS)

        binding?.apply {

            if (task != null) {
                editTextTitle.editText?.setText(task?.title)
                editTextDesc.editText?.setText(task?.description)
                textViewTime.text = task?.createdTime
                textViewSchedule.text = task?.scheduleTime
            } else {
                task = Task()
                textViewTime.text =
                    getString(R.string.created_at) + " " + DateToString.formatDate(Calendar.getInstance().time)
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

                task?.title = editTextTitle.editText?.text.toString()
                task?.description = editTextDesc.editText?.text.toString()
                task?.scheduleTime = textViewSchedule.text.toString()
                task?.createdTime = textViewTime.text.toString()

                if (task?.title?.isEmpty() == true) {
                    Toast.makeText(requireContext(), "Title cannot be empty.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    lifecycle.coroutineScope.launch {
                        task?.let { task ->
                            homeViewModel.insertTask(task) }
                        parentFragmentManager.popBackStack()
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
        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).build()
        datePicker.addOnPositiveButtonClickListener {

            calendar.timeInMillis = it
            timePicker.show(childFragmentManager, "TAG")
        }

        timePicker.addOnPositiveButtonClickListener {

            calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            calendar.set(Calendar.MINUTE, timePicker.minute)
            calendar.set(Calendar.SECOND, 5)

            task?.scheduleTime = DateToString.formatDate(calendar.time)

            binding?.textViewSchedule?.text = task?.scheduleTime
        }
        datePicker.show(childFragmentManager, "TAG")
    }

    private fun setAlarm(task: Task) {

        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        intent.putExtra("task_info", task)

        val pendingIntent = task.id.let {
            PendingIntent.getBroadcast(
                requireContext(),
                it,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

        }
        val mainActivityIntent = Intent(requireContext(), HomeActivity::class.java)
        val basicPendingIntent = task.id.let {
            PendingIntent.getActivity(
                requireContext(),
                it,
                mainActivityIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
        val clockInfo = AlarmManager.AlarmClockInfo(task.scheduleTime!!.toLong(), basicPendingIntent)
        alarmManager.setAlarmClock(clockInfo, pendingIntent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {

        Log.i("====", "CHECK 1")

        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel =
            NotificationChannel("to_do_list", "Tasks Notification Channel", importance).apply {
                description = "Notification for Tasks"
            }
        val notificationManager =
            activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}