package com.example.todokshitij.data.model

import com.google.gson.annotations.SerializedName

data class Widget(
    @SerializedName("personalizationType")
    val personalizationType : String?,
    @SerializedName("personalizationSequence")
    val personalizationSequence : ArrayList<PersonalizationSequence>?
)

data class PersonalizationSequence(
    @SerializedName("widget_id")
    val widgetId : String?,
    @SerializedName("widget_name")
    val widgetName : String?
)