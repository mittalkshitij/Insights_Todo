package com.example.todokshitij.data.repository

import com.example.todokshitij.ui.task.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAllTask() : Flow<List<Task>>

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)

}