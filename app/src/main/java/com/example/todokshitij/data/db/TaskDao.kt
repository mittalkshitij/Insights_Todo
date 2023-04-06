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

    @Query("SELECT * FROM Task")
    fun getAllTask() : Flow<List<Task>>

}