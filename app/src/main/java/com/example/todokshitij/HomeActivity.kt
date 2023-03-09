package com.example.todokshitij

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todokshitij.databinding.ActivityHomeBinding
import com.example.todokshitij.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}