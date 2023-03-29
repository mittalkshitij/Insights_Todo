package com.example.todokshitij.utils

import java.text.SimpleDateFormat
import java.util.*

var DATE_FORMAT_PATTERN = "dd/MM/yyyy ',' HH:mm"

fun formatDate(): String {
    return SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault()).format(Date())
}
