package com.example.todokshitij.ui.home.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todokshitij.R
import com.example.todokshitij.databinding.ActivityHomeBinding
import com.example.todokshitij.ui.task.adapter.TaskItemAdapter
import com.example.todokshitij.ui.task.model.Task
import com.example.todokshitij.ui.task.view.TaskFragment

class HomeActivity : AppCompatActivity(), TaskFragment.AddTaskListener {

    private lateinit var binding: ActivityHomeBinding
    private var taskList: ArrayList<Task> = arrayListOf()

    companion object{
        const val TASK_DETAILS = "TASK_DETAILS"
        const val TASK_POSITION = "TASK_POSITION"
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

    private fun setupOnClickListeners() {

        binding.buttonAdd.setOnClickListener {

            binding.buttonAdd.hide()
            binding.toolbar.menu.findItem(R.id.sort).isVisible = false

            supportFragmentManager.beginTransaction()
                .replace(R.id.clContainer, TaskFragment(this))
                .addToBackStack(null)
                .commit()
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
            R.id.sort -> Toast.makeText(this, "Sort Clicked", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
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