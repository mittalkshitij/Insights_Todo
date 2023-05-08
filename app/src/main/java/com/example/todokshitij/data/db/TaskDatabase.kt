package com.example.todokshitij.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.todokshitij.ui.task.model.Task
import java.util.*

@Database(entities = [Task::class], version = 18, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class TaskDatabase :RoomDatabase() {

    abstract fun getTaskDao() : TaskDao

    companion object{
        const val DB_NAME = "task_database.db"
    }
}

class DateConverter {
    @TypeConverter
    fun fromTimeStamp(value : Long) : Date = Date(value)
    @TypeConverter
    fun dateToTimeStamp(date : Date) : Long = date.time
}