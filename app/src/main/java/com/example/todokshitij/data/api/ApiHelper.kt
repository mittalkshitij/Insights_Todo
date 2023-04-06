package com.example.todokshitij.data.api

import com.example.todokshitij.data.model.Widget
import retrofit2.Response

interface ApiHelper {

    suspend fun getWidgetData() : Response<Widget>
}