package com.example.todokshitij.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todokshitij.ui.main.model.Login
import com.example.todokshitij.utils.ValidationStatus
import com.example.todokshitij.utils.isValidName
import com.example.todokshitij.utils.isValidPhone


class LoginViewModel : ViewModel() {

    private val _validationLiveData = MutableLiveData<ValidationStatus>()
    val validationLiveData: LiveData<ValidationStatus> = _validationLiveData

    fun validateLogin(loginData: Login) {

        if (isValidName(loginData.fullName) && isValidPhone(loginData.phoneNumber)
            && loginData.dob.length == 10
        ) {
            _validationLiveData.value = ValidationStatus.VALIDATION_SUCCESS
        } else if (!isValidName(loginData.fullName)) {
            _validationLiveData.value = ValidationStatus.VALIDATION_ERROR_NAME
        } else if (!isValidPhone(loginData.phoneNumber)) {
            _validationLiveData.value = ValidationStatus.VALIDATION_ERROR_PHONE
        } else {
            _validationLiveData.value = ValidationStatus.VALIDATION_ERROR_DOB
        }
    }
}

