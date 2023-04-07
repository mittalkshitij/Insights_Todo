package com.example.todokshitij.data.db

import androidx.room.*
import com.example.todokshitij.ui.task.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM Task ORDER BY id DESC")
    fun getAllTask() : Flow<List<Task>>

    @Query("SELECT * FROM TASK ORDER BY " +
            "CASE WHEN :sortOrder = 1 THEN schedule_time END ASC, \n" +
            "CASE WHEN :sortOrder = 2 THEN schedule_time END DESC")
    fun sortTaskByDate(sortOrder : Int) : Flow<List<Task>>
}