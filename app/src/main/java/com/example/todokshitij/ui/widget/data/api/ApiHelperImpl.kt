package com.example.todokshitij.ui.widget.data.api

import javax.inject.Inject

class ApiHelperImpl
@Inject constructor(private val apiInterface: ApiInterface) : ApiHelper {

    override suspend fun getWidgetData() = apiInterface.getWidgetData()
}