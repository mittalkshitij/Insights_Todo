package com.example.todokshitij.ui.widget.data.repository

import com.example.todokshitij.ui.widget.data.api.ApiHelper
import javax.inject.Inject


class WidgetRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getWidgetData() = apiHelper.getWidgetData()
}