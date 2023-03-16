package com.example.todokshitij.ui.intro.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.todokshitij.R
import com.example.todokshitij.databinding.ActivityIntroBinding
import com.example.todokshitij.ui.intro.adapter.IntroAdapter
import com.example.todokshitij.ui.main.view.MainActivity
import com.google.android.material.tabs.TabLayoutMediator

class IntroActivity : AppCompatActivity() {

    companion object {
        const val FRAGMENT_TITLE_KEY = "FragmentTitle"
        const val FRAGMENT_DESCRIPTION_KEY = "FragmentDesc"
        const val FRAGMENT_IMAGE_KEY = "FragmentImage"

        fun openIntroActivity(context: Context) {
            context.startActivity(Intent(context, IntroActivity::class.java))
        }
    }

    private lateinit var binding: ActivityIntroBinding
    private lateinit var introAdapter: IntroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        setupViewPager()
        setOnClickListeners()


    }

    private fun setOnClickListeners() {

        binding.buttonSkip.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupAdapter() {

        introAdapter = IntroAdapter(supportFragmentManager, lifecycle)

        val fragmentList = listOf(

            IntroFragment().apply {

                arguments = Bundle()
                arguments?.putString(FRAGMENT_TITLE_KEY, "Record")
                arguments?.putString(FRAGMENT_DESCRIPTION_KEY, "Record and create your day-to-day task")
                arguments?.putInt(FRAGMENT_IMAGE_KEY, R.drawable.baseline_note_add_24)
            }, IntroFragment().apply {

                arguments = Bundle()
                arguments?.putString(FRAGMENT_TITLE_KEY, "Schedule")
                arguments?.putString(FRAGMENT_DESCRIPTION_KEY, "Schedule your task to complete on time")
                arguments?.putInt(FRAGMENT_IMAGE_KEY, R.drawable.baseline_calendar_month_24)
            }, IntroFragment().apply {

                arguments = Bundle()
                arguments?.putString(FRAGMENT_TITLE_KEY, "Manage")
                arguments?.putString(FRAGMENT_DESCRIPTION_KEY, "Manage and plan according to your schedule")
                arguments?.putInt(FRAGMENT_IMAGE_KEY, R.drawable.baseline_task_24)
            })
        introAdapter.addFragment(fragment = fragmentList)
    }

    private fun setupViewPager() {

        binding.viewPager2.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = introAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { _, _ -> }.attach()

        binding.viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

                binding.buttonSkip.apply {
                    alpha = if (position == 2) {
                        setText(R.string.done)
                        1F
                    } else {
                        setText(R.string.skip)
                        0.7F
                    }
                }
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }
}