package com.example.todokshitij

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.todokshitij.databinding.ActivityHomeBinding
import com.example.todokshitij.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity(), TaskFragment.AddTaskListener {

    private lateinit var binding: ActivityHomeBinding
    private var taskList : ArrayList<Task> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        binding.recyclerView.adapter = TaskItemAdapter(taskList)
        //gettingData()

        binding.buttonAdd.setOnClickListener {

            binding.buttonAdd.hide()
            supportFragmentManager.beginTransaction().replace(R.id.coordinatorLayout,TaskFragment(this)).commit()
        }

    }

    override fun onAddTask(task: Task) {
        taskList.add(task)
        (binding.recyclerView.adapter as TaskItemAdapter).notifyItemInserted(taskList.indexOf(task))

        binding.buttonAdd.show()
        supportFragmentManager.popBackStack()
        supportFragmentManager.findFragmentById(R.id.coordinatorLayout)
            ?.let { supportFragmentManager.beginTransaction().remove(it).commit() }
    }

}