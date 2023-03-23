package com.example.todokshitij.data.api

class ApiHelper(private val apiInterface: ApiInterface) {

    suspend fun getWidgetData() = apiInterface.getWidgetData()
}