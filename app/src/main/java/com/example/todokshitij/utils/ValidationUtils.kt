package com.example.todokshitij.utils

import com.example.todokshitij.utils.Constants.MAX_VALID_YEAR
import com.example.todokshitij.utils.Constants.MIN_VALID_YEAR


fun isValidPhone(phone : String): Boolean {

    if (Regex("[1-9]\\d{9}").matches(phone) && phone.length == 10) {
        return true
    }
    return false
}

fun isValidName(fullName : String): Boolean {

    if (Regex("^([a-zA-Z]{2,}\\s[a-zA-Z]{2,}\\s?[a-zA-Z]*)").matches(fullName)) {
        return true
    }
    return false
}


fun checkValidDate(day: Int, month: Int, year: Int): Boolean {

    if (year > MAX_VALID_YEAR || year < MIN_VALID_YEAR)
        return false
    if (month < 1 || month > 12) return false
    if (day < 1 || day > 31) return false

    if (month == 2) {
        return if (isLeap(year)) day <= 29 else day <= 28
    }
    return if (month == 4 || month == 6 || month == 9 || month == 11
    ) day <= 30 else true
}

private fun isLeap(year: Int): Boolean = year % 4 == 0 && year % 100 != 0 || year % 400 == 0