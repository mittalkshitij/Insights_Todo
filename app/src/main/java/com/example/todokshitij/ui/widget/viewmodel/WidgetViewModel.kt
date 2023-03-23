package com.example.todokshitij.ui.widget.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.todokshitij.data.model.Widget
import com.example.todokshitij.data.repository.WidgetRepository
import com.example.todokshitij.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WidgetViewModel(private val widgetRepository: WidgetRepository) : ViewModel() {

    private val _widgetData = MutableLiveData<Widget>()
    val widgetData: LiveData<Widget>
        get() = _widgetData


}