package com.example.todokshitij.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.todokshitij.R
import com.example.todokshitij.data.prefs.InsightsSharedPreferences
import com.example.todokshitij.data.prefs.InsightsSharedPreferences.sharedPreferences
import com.example.todokshitij.databinding.ActivitySplashScreenBinding
import com.example.todokshitij.ui.home.view.HomeActivity
import com.example.todokshitij.ui.intro.view.IntroActivity
import com.example.todokshitij.utils.Constants.SPLASH_SCREEN_DURATION


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        InsightsSharedPreferences.createEncryptedSharedPreference(this)

        setupAnimation()
        setupHandler()
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

        val fullName = sharedPreferences?.getString("user_name","")

        if (fullName?.isNotEmpty() == true) {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            IntroActivity.openIntroActivity(this)
            overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation)
            finish()
        }
    }
}