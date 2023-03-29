package com.example.todokshitij.ui.widget.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.todokshitij.data.repository.WidgetRepository
import com.example.todokshitij.utils.Resource
import kotlinx.coroutines.Dispatchers


class WidgetViewModel(private val widgetRepository: WidgetRepository) : ViewModel() {

    fun getWidgetData() = liveData(Dispatchers.IO){
        emit(Resource.loading(null))

        try {
            emit(Resource.success(data = widgetRepository.getWidgetData()))
        }catch (exception: Exception){
            emit(Resource.error(null,exception.message?:"Error Occurred!"))
        }
    }
}