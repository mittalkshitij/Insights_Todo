package com.example.todokshitij.data.api

import javax.inject.Inject

class ApiHelperImpl
@Inject constructor(private val apiInterface: ApiInterface) : ApiHelper {

    override suspend fun getWidgetData() = apiInterface.getWidgetData()
}