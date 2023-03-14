package com.example.todokshitij

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.todokshitij.databinding.FragmentIntroBinding

class IntroFragment : Fragment() {

    var binding : FragmentIntroBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentIntroBinding.inflate(layoutInflater,container,false)

        binding?.textViewTitle?.text = arguments?.getString("FragmentTitle","")
        binding?.textViewDescription?.text = arguments?.getString("FragmentDesc","")
        binding?.imageViewLogo?.setImageDrawable(arguments?.getInt("FragmentImage")
            ?.let { AppCompatResources.getDrawable(requireContext(),it) })

        return binding?.root
    }
}