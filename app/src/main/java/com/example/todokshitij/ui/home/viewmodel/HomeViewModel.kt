package com.example.todokshitij.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todokshitij.data.repository.TaskRepositoryImpl
import com.example.todokshitij.ui.task.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val taskRepository: TaskRepositoryImpl): ViewModel() {

    suspend fun insertTask(task : Task) = taskRepository.insertTask(task)

    suspend fun updateTask(task : Task) = taskRepository.updateTask(task)

    suspend fun deleteTask(task : Task) = taskRepository.deleteTask(task)

    fun getAllNotes() = taskRepository.getAllTask()

    fun sortTaskByDate(sortOrder : Int) = taskRepository.sortTaskByDate(sortOrder)
}