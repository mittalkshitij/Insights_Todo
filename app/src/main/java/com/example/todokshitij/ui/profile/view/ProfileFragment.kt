package com.example.todokshitij.ui.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todokshitij.R
import com.example.todokshitij.data.prefs.InsightsSharedPreferences.sharedPreferences
import com.example.todokshitij.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)

        setUserDetails()

        return binding?.root
    }

    private fun setUserDetails() {

        binding?.apply {
            textViewName.text = getString(R.string.username, sharedPreferences?.getString("user_name",""))
            textViewMobileNo.text = getString(R.string.mobileno, sharedPreferences?.getString("phone_no",""))
            textViewDob.text = getString(R.string.dobUser , sharedPreferences?.getString("user_dob",""))
        }
    }
}
