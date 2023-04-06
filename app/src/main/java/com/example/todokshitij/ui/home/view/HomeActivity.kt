package com.example.todokshitij.ui.home.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todokshitij.R
import com.example.todokshitij.databinding.ActivityHomeBinding
import com.example.todokshitij.ui.task.adapter.TaskItemAdapter
import com.example.todokshitij.ui.task.model.Task
import com.example.todokshitij.ui.task.view.TaskFragment
import com.example.todokshitij.ui.widget.view.WidgetActivity
import com.example.todokshitij.utils.Constants.TASK_DETAILS
import com.example.todokshitij.utils.Constants.TASK_POSITION
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.*


class HomeActivity : AppCompatActivity(), TaskFragment.AddTaskListener {

    private lateinit var binding: ActivityHomeBinding
    private var taskList: ArrayList<Task> = arrayListOf()

    private var wayLatitude = 0.0
    private var wayLongitude = 0.0

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {
        private const val REQUEST_CODE = 100
    }

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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressedDispatcher.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }

    private fun setupOnClickListeners() {

        binding.buttonAdd.setOnClickListener {

            binding.buttonAdd.hide()
            binding.toolbar.menu.findItem(R.id.sort).isVisible = false

            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                .replace(R.id.clContainer, TaskFragment(this))
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
        binding.recyclerView.adapter = TaskItemAdapter(
            taskList, object :
                TaskItemAdapter.RemoveTaskListener {

                override fun removeTask(task: Task) {
                    (binding.recyclerView.adapter as TaskItemAdapter).notifyItemRemoved(
                        taskList.indexOf(task)
                    )
                    taskList.remove(task)
                }
            },
            object : TaskItemAdapter.TaskClickListener {

                override fun onTaskClick(taskPosition: Int) {

                    binding.buttonAdd.hide()
                    binding.toolbar.menu.findItem(R.id.sort).isVisible = false

                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                        .replace(R.id.clContainer, TaskFragment(this@HomeActivity).apply {
                            arguments = Bundle()
                            arguments?.putParcelable(TASK_DETAILS, taskList[taskPosition])
                            arguments?.putInt(TASK_POSITION, taskPosition)
                        })
                        .addToBackStack(null)
                        .commit()

                }
            })
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

    override fun onAddTask(task: Task) {

        taskList.add(task)
        (binding.recyclerView.adapter as TaskItemAdapter).notifyItemInserted(
            taskList.indexOf(task)
        )
        binding.buttonAdd.show()
        binding.toolbar.menu.findItem(R.id.sort).isVisible = true
        supportFragmentManager.popBackStack()
    }

    override fun onEditTask(task: Task, position: Int) {

        taskList[position] = task
        (binding.recyclerView.adapter as TaskItemAdapter).notifyItemChanged(
            taskList.indexOf(task)
        )
        supportFragmentManager.popBackStack()

    }
}