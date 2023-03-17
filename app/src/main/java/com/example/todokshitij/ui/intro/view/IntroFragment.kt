package com.example.todokshitij.ui.intro.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.todokshitij.databinding.FragmentIntroBinding
import com.example.todokshitij.utils.Constants.FRAGMENT_DESCRIPTION_KEY
import com.example.todokshitij.utils.Constants.FRAGMENT_IMAGE_KEY
import com.example.todokshitij.utils.Constants.FRAGMENT_TITLE_KEY

class IntroFragment : Fragment() {

    var binding : FragmentIntroBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentIntroBinding.inflate(layoutInflater,container,false)

        binding?.textViewTitle?.text = arguments?.getString(FRAGMENT_TITLE_KEY)
        binding?.textViewDescription?.text = arguments?.getString(FRAGMENT_DESCRIPTION_KEY)
        binding?.imageViewLogo?.setImageDrawable(arguments?.getInt(FRAGMENT_IMAGE_KEY)
            ?.let { AppCompatResources.getDrawable(requireContext(),it) })

        return binding?.root
    }
}