package com.example.todokshitij.data.repository

import com.example.todokshitij.data.db.TaskDao
import com.example.todokshitij.ui.task.model.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao) : TaskRepository {

    override fun getAllTask(): Flow<List<Task>> = taskDao.getAllTask()

    override suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    override suspend fun updateTask(task: Task) = taskDao.updateTask(task)
}