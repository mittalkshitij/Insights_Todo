package com.example.todokshitij

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.todokshitij.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.sample_anim)
        binding.imageView.startAnimation(slideAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this@SplashScreenActivity, IntroActivity::class.java).apply {
                putExtra("",true)
            }
            startActivity(i)
            finish()
        }, 4000)

        setContentView(binding.root)
    }
}