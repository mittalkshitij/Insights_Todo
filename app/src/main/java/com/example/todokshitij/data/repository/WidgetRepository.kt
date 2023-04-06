package com.example.todokshitij.data.repository

import com.example.todokshitij.data.api.ApiHelper
import javax.inject.Inject


class WidgetRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getWidgetData() = apiHelper.getWidgetData()
}