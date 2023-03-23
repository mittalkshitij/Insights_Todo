package com.example.todokshitij.data.repository

import com.example.todokshitij.data.api.ApiHelper


class WidgetRepository(private val apiHelper: ApiHelper) {

    suspend fun getWidgetData() = apiHelper.getWidgetData()
}