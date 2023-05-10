package com.example.todokshitij.ui.profile.view

import android.app.Activity
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageAndVideo
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.todokshitij.R
import com.example.todokshitij.data.prefs.InsightsSharedPreferences.sharedPreferences
import com.example.todokshitij.databinding.FragmentProfileBinding
import com.google.android.material.snackbar.Snackbar


class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null

    private lateinit var pickSingleMediaLauncher: ActivityResultLauncher<PickVisualMediaRequest>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)

        pickSingleMediaLauncher = registerForActivityResult(PickVisualMedia()) { uri->
            binding?.imageView2?.setImageURI(uri)
            showSnackBar("SUCCESS: ${uri?.path}")
        }
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//                if (it.resultCode != Activity.RESULT_OK) {
//                    Toast.makeText(requireContext(), "Failed picking media.", Toast.LENGTH_SHORT).show()
//                } else {
//                    val uri = it.data?.data
//                    binding?.imageView2?.setImageURI(uri)
//                    showSnackBar("SUCCESS: ${uri?.path}")
//                }
//            }

        setProfilePicture()
        setUserDetails()

        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setProfilePicture() {

        binding?.profileButton?.setOnClickListener {
            pickSingleMediaLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
    }

    private fun setUserDetails() {

        binding?.apply {
            textViewName.text = getString(R.string.username, sharedPreferences?.getString("user_name",""))
            textViewMobileNo.text = getString(R.string.mobileno, sharedPreferences?.getString("phone_no",""))
            textViewDob.text = getString(R.string.dobUser , sharedPreferences?.getString("user_dob",""))
        }
    }

    private fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(
            requireView(),
            message,
            Snackbar.LENGTH_LONG,
        )
        // Set the max lines of SnackBar
        snackBar.show()
    }
}
