package com.example.todokshitij.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todokshitij.databinding.FragmentTaskCompletedBinding

class CompletedTaskFragment : Fragment() {

    private var binding : FragmentTaskCompletedBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskCompletedBinding.inflate(layoutInflater,container,false)

        return binding?.root
    }
}