package com.example.todokshitij.br

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todokshitij.R
import com.example.todokshitij.ui.home.view.HomeActivity
import com.example.todokshitij.ui.task.model.Task

class AlarmReceiver : BroadcastReceiver() {

    private var notificationManager: NotificationManagerCompat? = null

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(p0: Context?, p1: Intent?) {

        val taskInfo = p1?.getParcelableExtra("task_info", Task::class.java)

        Log.i("====","CHECK 3")

        val tapResultIntent = Intent(p0, HomeActivity::class.java)
        tapResultIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent: PendingIntent =
            getActivity(p0, 0, tapResultIntent, FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE)

        val notification = p0?.let {
            NotificationCompat.Builder(it, "to_do_list")
                .setContentTitle(taskInfo?.title)
                .setContentText(taskInfo?.description)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        }

        notificationManager = p0?.let { NotificationManagerCompat.from(it) }

        notification?.let { taskInfo?.let { it1 -> notificationManager?.notify(it1.id!!, it) } }
    }
}

