package com.example.todokshitij

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todokshitij.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity(), TaskFragment.AddTaskListener {

    private lateinit var binding: ActivityHomeBinding
    private var taskList: ArrayList<Task> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = intent.getStringExtra("name")


        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.findFragmentById(R.id.clContainer) !is TaskFragment) {
                binding.buttonAdd.show()
                binding.toolbar.menu.findItem(R.id.sort).isVisible = true
            }
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = TaskItemAdapter(taskList, object :
            TaskItemAdapter.RemoveTaskListener {

            override fun removeTask(task: Task) {
                (binding.recyclerView.adapter as TaskItemAdapter).notifyItemRemoved(
                    taskList.indexOf(task)
                )
                taskList.remove(task)
            }
        })

        binding.buttonAdd.setOnClickListener {
            binding.buttonAdd.hide()

            binding.toolbar.menu.findItem(R.id.sort).isVisible = false

            supportFragmentManager.beginTransaction()
                .replace(R.id.clContainer, TaskFragment(this))
                .addToBackStack(null)
                .commit()
        }
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
//        onBackPressedDispatcher.onBackPressed()
    }
}