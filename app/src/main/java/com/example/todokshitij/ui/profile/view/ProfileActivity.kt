package com.example.todokshitij.ui.profile.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todokshitij.R
import com.example.todokshitij.data.prefs.InsightsSharedPreferences.sharedPreferences
import com.example.todokshitij.databinding.ActivityHomeBinding
import com.example.todokshitij.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    var binding : ActivityProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUserDetails()
    }

    private fun setUserDetails() {

        binding?.apply {
            textViewName.text = "Name : " + sharedPreferences?.getString("user_name","")
            textViewMobileNo.text = "Mobile No : " + sharedPreferences?.getString("phone_no","")
            textViewDob.text = "Dob : " + sharedPreferences?.getString("user_dob","")
        }
    }
}