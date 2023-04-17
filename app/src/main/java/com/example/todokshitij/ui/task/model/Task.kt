package com.example.todokshitij.ui.task.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todokshitij.utils.Constants
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id : Int?,
    @ColumnInfo(name = "title")
    val title : String,
    @ColumnInfo(name = "description")
    val description : String,
    @ColumnInfo(name = "created_time")
    val createdTime : Date,
    @ColumnInfo(name = "schedule_time")
    var scheduleTime : Date
) : Parcelable