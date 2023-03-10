package com.example.todokshitij

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todokshitij.databinding.ActivityMainBinding
import java.util.Calendar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var MAX_VALID_YEAR = Calendar.getInstance().get(Calendar.YEAR)
    private var MIN_VALID_YEAR = Calendar.getInstance().get(Calendar.YEAR) - 50

    private var name : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {

            if (isValidName() && isValidPhone() && binding.editTextDob.editText?.text?.length== 10) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("name",name)
                startActivity(intent)
            }else if (!isValidName()){
                binding.editTextName.editText?.error = "Invalid Name"
            }else if (!isValidPhone()){
                binding.editTextMobileNo.editText?.error = "Invalid Phone"
            }else{

                binding.editTextDob.editText?.error = "Invalid Date"
            }
        }

        binding.editTextDob.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                binding.editTextDob.editText?.apply {

                    val str: String = text.toString()
                    val textLength: Int = text.length

                    if (textLength == 3 || textLength == 6 ) {

                            setText(
                                StringBuilder(text.toString()).insert(str.length - 1, "/")
                                    .toString()
                            )
                            setSelection(length())
                    }
                    if (textLength == 10) {

                        val dd = text.substring(0, 2)
                        val mm = text.substring(3, 5)
                        val yyyy = text.substring(6)

                        if (!checkValidDate(dd.toInt(), mm.toInt(), yyyy.toInt())) {

                            binding.editTextDob.editText?.error = "Invalid Date"
                            binding.buttonLogin.isClickable = false

                        }else{
                            binding.buttonLogin.isClickable = true

                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }


    private fun isValidPhone(): Boolean {

        val phone = binding.editTextMobileNo.editText?.text.toString()
        if(Regex("[1-9]\\d{9}").matches(phone) && phone.length == 10){
            return true
            }
        return false
    }

    private fun isValidName() : Boolean{
        name = binding.editTextName.editText?.text.toString()
        if (Regex("^([a-zA-Z]{2,}\\s[a-zA-Z]{2,}\\s?[a-zA-Z]*)").matches(name)){
            return true
        }
        return false
    }


    fun checkValidDate(d: Int, m: Int, y: Int): Boolean {

        if (y > MAX_VALID_YEAR || y < MIN_VALID_YEAR)
            return false
        if (m < 1 || m > 12) return false
        if (d < 1 || d > 31) return false

        if (m == 2) {
            return if (isLeap(y)) d <= 29 else d <= 28
        }
        return if (m == 4 || m == 6 || m == 9 || m == 11
        ) d <= 30 else true
    }
    private fun isLeap(year: Int): Boolean {

        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }

}