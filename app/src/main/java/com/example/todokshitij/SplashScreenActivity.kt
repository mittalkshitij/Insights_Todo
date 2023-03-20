package com.example.todokshitij

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.todokshitij.databinding.ActivitySplashScreenBinding
import com.example.todokshitij.ui.intro.view.IntroActivity
import com.example.todokshitij.utils.Constants.SPLASH_SCREEN_DURATION


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        setupAnimation()
        setupHandler()
        setContentView(binding.root)
    }

    private fun setupHandler(){

        Handler(Looper.getMainLooper()).postDelayed({
            openActivity()
        }, SPLASH_SCREEN_DURATION.toLong())
    }

    private fun setupAnimation() {

        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_fall_down)
        binding.imageView.startAnimation(slideAnimation)
    }

    private fun openActivity() {
        IntroActivity.openIntroActivity(this)
        overridePendingTransition(R.anim.enter_animation,R.anim.exit_animation)
        finish()
    }
}