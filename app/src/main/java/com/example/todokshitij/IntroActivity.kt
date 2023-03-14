package com.example.todokshitij

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.todokshitij.databinding.ActivityIntroBinding
import com.google.android.material.tabs.TabLayoutMediator

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding
    private lateinit var introAdapter: IntroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        introAdapter = IntroAdapter(supportFragmentManager, lifecycle)

        val fragmentList = listOf(

            IntroFragment().apply {

                arguments = Bundle()
                arguments?.putString("FragmentTitle", "Record")
                arguments?.putString("FragmentDesc", "Record and create your day-to-day task")
                arguments?.putInt("FragmentImage", R.drawable.baseline_note_add_24)
            },
            IntroFragment().apply {

                arguments = Bundle()
                arguments?.putString("FragmentTitle", "Schedule")
                arguments?.putString("FragmentDesc", "Schedule your task to complete on time")
                arguments?.putInt("FragmentImage", R.drawable.baseline_calendar_month_24)
            },
            IntroFragment().apply {

                arguments = Bundle()
                arguments?.putString("FragmentTitle", "Manage")
                arguments?.putString("FragmentDesc","Manage and plan according to your schedule")
                arguments?.putInt("FragmentImage", R.drawable.baseline_task_24)
            }

        )

        introAdapter.addFragment(fragment = fragmentList)

        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager2.adapter = introAdapter


        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, i ->

        }.attach()

        binding.viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

                if (position == 2) {

                    binding.buttonSkip.apply {
                        text = "Done"
                        alpha = 1F
                    }
                } else {
                    binding.buttonSkip.apply {
                        text = "Skip"
                        alpha = 0.8F
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        binding.buttonSkip.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}