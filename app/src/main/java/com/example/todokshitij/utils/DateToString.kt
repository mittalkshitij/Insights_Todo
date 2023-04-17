package com.example.todokshitij.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

//var DATE_FORMAT_PATTERN = "dd/MM/yyyy ',' HH:mm a"
val format1 = "dd/MM/yyyy"
val format2 = "dd/MM/yyyy ',' HH:mm a"

class DateToString {

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun formatDate(date: Date?): String {

            return if(date?.seconds == 0){
                val df = SimpleDateFormat(format1)
                df.format(date)
            }else {
                val df = SimpleDateFormat(format2)
                df.format(date)
            }
        }
    }
}
