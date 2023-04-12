package com.example.todokshitij.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todokshitij.R
import com.example.todokshitij.data.prefs.InsightsSharedPreferences
import com.example.todokshitij.data.prefs.InsightsSharedPreferences.sharedPreferences
import com.example.todokshitij.databinding.ActivityMainBinding
import com.example.todokshitij.ui.home.view.HomeActivity
import com.example.todokshitij.ui.main.model.Login
import com.example.todokshitij.ui.main.viewmodel.LoginViewModel
import com.example.todokshitij.utils.Constants.ERROR_DATE
import com.example.todokshitij.utils.Constants.ERROR_NAME
import com.example.todokshitij.utils.Constants.ERROR_PHONE_NUMBER
import com.example.todokshitij.utils.ValidationStatus
import com.example.todokshitij.utils.checkValidDate


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var loginViewModel: LoginViewModel? = null

    private var fullName: String? = null
    private var phoneNumber: String? = null
    private var dob: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        setOnClickListeners()
        observeLoginData()
        handleDataText()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressedDispatcher.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }

    private fun observeLoginData() {

        loginViewModel?.validationLiveData?.observe(this) {

            when (it) {
                ValidationStatus.VALIDATION_SUCCESS -> {

                    with(sharedPreferences!!.edit()) {
                        putString("user_name", fullName)
                        putString("phone_no", phoneNumber)
                        putString("user_dob", dob)
                        commit()
                    }
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation)
                    finish()
                }
                ValidationStatus.VALIDATION_ERROR_NAME -> {
                    binding.editTextName.editText?.error = ERROR_NAME
                }
                ValidationStatus.VALIDATION_ERROR_PHONE -> {
                    binding.editTextMobileNo.editText?.error = ERROR_PHONE_NUMBER
                }
                ValidationStatus.VALIDATION_ERROR_DOB -> {
                    binding.editTextDob.editText?.error = ERROR_DATE
                }
            }
        }
    }

    private fun setOnClickListeners() {

        binding.buttonLogin.setOnClickListener {

            fullName = binding.editTextName.editText?.text.toString()
            phoneNumber = binding.editTextMobileNo.editText?.text.toString()
            dob = binding.editTextDob.editText?.text.toString()

            loginViewModel?.validateLogin(Login(fullName!!, phoneNumber!!, dob!!))

        }
    }

    private fun handleDataText() {

        var beforeTextLength = 0

        binding.editTextDob.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                beforeTextLength = s.length
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {

                binding.editTextDob.editText?.apply {

                    val str: String = text.toString()
                    val textLength: Int = text.length

                    if (textLength == 3 || textLength == 6) {
                        if (textLength > beforeTextLength) {
                            setText(
                                StringBuilder(text.toString()).insert(str.length - 1, "/")
                                    .toString()
                            )
                        }
                        setSelection(length())
                    }
                    if (textLength == 10) {

                        val dd = text.substring(0, 2)
                        val mm = text.substring(3, 5)
                        val yyyy = text.substring(6)

                        if (!checkValidDate(dd.toInt(), mm.toInt(), yyyy.toInt())) {

                            binding.editTextDob.editText?.error = "Invalid Date"
                            binding.buttonLogin.isClickable = false

                        } else {
                            binding.buttonLogin.isClickable = true

                        }
                    }
                }
            }
        })
    }
}