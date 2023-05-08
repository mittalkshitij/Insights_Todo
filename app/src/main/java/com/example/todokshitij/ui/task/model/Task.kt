package com.example.todokshitij.ui.task.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity
data class Task(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "created_time")
    var createdTime: String? = "",
    @ColumnInfo(name = "schedule_time")
    var scheduleTime: String? = ""
) : Parcelable