package com.example.todokshitij.utils

import java.util.*

object Constants {

    const val SPLASH_SCREEN_DURATION = 5000

    val MAX_VALID_YEAR = Calendar.getInstance().get(Calendar.YEAR)
    val MIN_VALID_YEAR = Calendar.getInstance().get(Calendar.YEAR) - 50

    const val ERROR_NAME = "Invalid Name"
    const val ERROR_PHONE_NUMBER = "Invalid Phone Number"
    const val ERROR_DATE = "Invalid Date"

    const val FRAGMENT_TITLE_KEY = "FragmentTitle"
    const val FRAGMENT_DESCRIPTION_KEY = "FragmentDesc"
    const val FRAGMENT_IMAGE_KEY = "FragmentImage"

    const val TASK_DETAILS = "TASK_DETAILS"
    const val TASK_POSITION = "TASK_POSITION"
}
