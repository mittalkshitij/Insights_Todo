package com.example.todokshitij.ui.home.view

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todokshitij.R
import com.example.todokshitij.data.prefs.InsightsSharedPreferences
import com.example.todokshitij.data.prefs.InsightsSharedPreferences.sharedPreferences
import com.example.todokshitij.databinding.ActivityHomeBinding
import com.example.todokshitij.ui.home.viewmodel.HomeViewModel
import com.example.todokshitij.ui.profile.view.ProfileActivity
import com.example.todokshitij.ui.task.adapter.TaskItemAdapter
import com.example.todokshitij.ui.task.model.Task
import com.example.todokshitij.ui.task.view.TaskFragment
import com.example.todokshitij.ui.widget.view.WidgetActivity
import com.example.todokshitij.utils.Constants.TASK_DETAILS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), TaskItemAdapter.RemoveTaskListener {

    private lateinit var binding: ActivityHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var taskItemAdapter: TaskItemAdapter

    private var sortCheck: Boolean? = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupRecyclerView()
        setupOnClickListeners()

        supportFragmentManager.addOnBackStackChangedListener {

            if (supportFragmentManager.findFragmentById(R.id.clContainer) !is TaskFragment) {
                binding.buttonAdd.show()
                binding.toolbar.menu.findItem(R.id.sort).isVisible = true
                binding.toolbar.menu.findItem(R.id.apiCallButton).isVisible = true
            }
        }
    }

    private fun setupOnClickListeners() {

        binding.buttonAdd.setOnClickListener {
            binding.buttonAdd.hide()
            binding.toolbar.menu.findItem(R.id.sort).isVisible = false
            binding.toolbar.menu.findItem(R.id.apiCallButton).isVisible = false
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                .replace(R.id.clContainer, TaskFragment())
                .addToBackStack(null)
                .commit()
        }

    }

    private fun setupActionBar() {

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayUseLogoEnabled(true)
            setDisplayShowHomeEnabled(true)

            title = sharedPreferences?.getString("user_name","hi").toString()
        }
    }

    private fun setupRecyclerView() {

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        taskItemAdapter = TaskItemAdapter({
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                .replace(R.id.clContainer, TaskFragment().apply {
                    arguments = Bundle()
                    arguments?.putParcelable(TASK_DETAILS, it)
                    binding.buttonAdd.hide()
                    binding.toolbar.menu.findItem(R.id.sort).isVisible = false
                    binding.toolbar.menu.findItem(R.id.apiCallButton).isVisible = false
                }).addToBackStack(null)
                .commit()
        }, this)

        binding.recyclerView.adapter = taskItemAdapter

        lifecycle.coroutineScope.launch {
            homeViewModel.getAllNotes().collect{
                taskItemAdapter.submitList(it)
            }
        }
    }

    override fun removeTask(task: Task) {

        lifecycle.coroutineScope.launch {
            homeViewModel.deleteTask(task)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressedDispatcher.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort -> sortTaskByDate()
            R.id.apiCallButton -> callApi()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun callApi() {

        val intent = Intent(this, WidgetActivity::class.java)
        startActivity(intent)
    }

    private fun sortTaskByDate() {

        lifecycle.coroutineScope.launch {
            if (sortCheck != true) {
                homeViewModel.sortTaskByDate(1).collect {
                    taskItemAdapter.submitList(it)
                    sortCheck = true
                }
            } else {
                homeViewModel.sortTaskByDate(2).collect{
                    taskItemAdapter.submitList(it)
                    sortCheck = false
                }
            }
        }
    }

}