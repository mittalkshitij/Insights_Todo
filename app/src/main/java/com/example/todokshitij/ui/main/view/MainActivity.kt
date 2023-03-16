package com.example.todokshitij.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.todokshitij.databinding.ActivityMainBinding
import com.example.todokshitij.ui.home.view.HomeActivity
import java.util.Calendar

const val ERROR_NAME = "Invalid Name"
const val ERROR_PHONE_NUMBER = "Invalid Phone Number"
const val ERROR_DATE = "Invalid Date"

class MainActivity : AppCompatActivity() {

    companion object {
        private var MAX_VALID_YEAR = Calendar.getInstance().get(Calendar.YEAR)
        private var MIN_VALID_YEAR = Calendar.getInstance().get(Calendar.YEAR) - 50
        private var full_name: String = ""
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
        handleDataText()
    }

    private fun setOnClickListeners() {

        binding.buttonLogin.setOnClickListener {

            if (isValidName() && isValidPhone() && binding.editTextDob.editText?.text?.length == 10) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("name", full_name)
                startActivity(intent)
            } else if (!isValidName()) {
                binding.editTextName.editText?.error = ERROR_NAME
            } else if (!isValidPhone()) {
                binding.editTextMobileNo.editText?.error = ERROR_PHONE_NUMBER
            } else {
                binding.editTextDob.editText?.error = ERROR_DATE
            }
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


    private fun isValidPhone(): Boolean {

        val phone = binding.editTextMobileNo.editText?.text.toString()
        if (Regex("[1-9]\\d{9}").matches(phone) && phone.length == 10) {
            return true
        }
        return false
    }

    private fun isValidName(): Boolean {
        full_name = binding.editTextName.editText?.text.toString()
        if (Regex("^([a-zA-Z]{2,}\\s[a-zA-Z]{2,}\\s?[a-zA-Z]*)").matches(full_name)) {
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
}