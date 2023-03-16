package com.example.todokshitij.ui.task.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(

    val title : String,
    val description : String,
    val createdTime : String,
    val scheduleTime : String
) : Parcelable