package com.example.todokshitij.ui.home.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todokshitij.R
import com.example.todokshitij.databinding.ActivityHomeBinding
import com.example.todokshitij.ui.home.viewmodel.HomeViewModel
import com.example.todokshitij.ui.task.adapter.TaskItemAdapter
import com.example.todokshitij.ui.task.model.Task
import com.example.todokshitij.ui.task.view.TaskFragment
import com.example.todokshitij.ui.widget.view.WidgetActivity
import com.example.todokshitij.utils.Constants.TASK_DETAILS
import com.example.todokshitij.utils.Constants.TASK_POSITION
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(),TaskItemAdapter.RemoveTaskListener{

    private lateinit var binding: ActivityHomeBinding
    private var taskList: ArrayList<Task> = arrayListOf()

    private val homeViewModel : HomeViewModel by viewModels()

    private lateinit var taskItemAdapter : TaskItemAdapter

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
            }
        }
    }

    private fun setupOnClickListeners() {

        binding.buttonAdd.setOnClickListener {
            binding.buttonAdd.hide()
            binding.toolbar.menu.findItem(R.id.sort).isVisible = false
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                .replace(R.id.clContainer, TaskFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.buttonApiCall.setOnClickListener {
            val intent = Intent(this, WidgetActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayUseLogoEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = intent.getStringExtra("name")
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
                    arguments?.putParcelable(TASK_DETAILS,it)
                }).addToBackStack(null)
                .commit()
        },this)

        binding.recyclerView.adapter = taskItemAdapter

        lifecycle.coroutineScope.launch {
            homeViewModel.getAllNotes().collect(){
                taskItemAdapter.submitList(it)
            }
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
            R.id.sort -> sortDate()
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortDate() {

        Toast.makeText(this, "Sort Clicked", Toast.LENGTH_LONG).show()
        Collections.sort(taskList, dateComparator)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private val dateComparator = Comparator<Task> { date1, date2 ->
        if (date1.scheduleTime.isNotEmpty() && date2.scheduleTime.isNotEmpty()) {
            if (date1.scheduleTime < date2.scheduleTime) {
                return@Comparator date1.scheduleTime.compareTo(date2.scheduleTime)
            } else {
                return@Comparator date2.scheduleTime.compareTo(date1.scheduleTime)
            }
        } else
            return@Comparator 0
    }

    override fun removeTask(task: Task) {

        lifecycle.coroutineScope.launch {
            homeViewModel.deleteTask(task)
        }
    }
}