package com.example.todokshitij.ui.widget.data.api

import com.example.todokshitij.ui.widget.data.model.Widget
import retrofit2.Response

interface ApiHelper {

    suspend fun getWidgetData() : Response<Widget>
}