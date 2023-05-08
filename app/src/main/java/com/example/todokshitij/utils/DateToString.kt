package com.example.todokshitij.utils

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_PATTERN = "dd/MM/yyyy ',' HH:mm a"

class DateToString {

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun formatDate(date: Date) : String? {
            return SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault()).format(date)
        }
    }

}
