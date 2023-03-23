package com.example.todokshitij.data.api

import com.example.todokshitij.data.model.Widget
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("content/dam/insurance-mall/homepage/homepage-bfl/android/android_homepage_bfl.json")
    suspend fun getWidgetData() : Response<Widget>
}