package com.example.todokshitij.ui.widget.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todokshitij.data.api.ApiHelper
import com.example.todokshitij.data.repository.WidgetRepository

@Suppress("UNCHECKED_CAST")
class WidgetViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(WidgetViewModel::class.java)) {
            return WidgetViewModel(WidgetRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}