package com.example.todokshitij.ui.home.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todokshitij.R
import com.example.todokshitij.databinding.FragmentTaskHomeBinding
import com.example.todokshitij.ui.home.viewmodel.HomeViewModel
import com.example.todokshitij.ui.task.adapter.TaskItemAdapter
import com.example.todokshitij.ui.task.model.Task
import com.example.todokshitij.ui.task.view.TaskFragment
import com.example.todokshitij.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeTaskFragment : Fragment(), TaskItemAdapter.RemoveTaskListener {

    private var binding: FragmentTaskHomeBinding? = null

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var taskItemAdapter: TaskItemAdapter

    private var sortCheck: Boolean? = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskHomeBinding.inflate(layoutInflater, container, false)

        createMenu()
        setupRecyclerView()
        setupOnClickListeners()

        return binding?.root
    }

        private fun createMenu() {

        if (activity != null) {

            activity?.addMenuProvider(object : MenuProvider {

                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.sort_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                    when (menuItem.itemId) {
                        R.id.sort -> sortTaskByDate()
                    }
                    return true
                }
            }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        }
    }

    private fun setupRecyclerView() {

        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        taskItemAdapter = TaskItemAdapter({
            arguments = Bundle()
            arguments?.putParcelable(Constants.TASK_DETAILS, it)
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                .replace(R.id.fragmentContainerView, TaskFragment::class.java, arguments).addToBackStack(null)
                .commit()
        }, this)

        binding?.recyclerView?.adapter = taskItemAdapter

        lifecycle.coroutineScope.launch {
            homeViewModel.getAllNotes().collect {
                taskItemAdapter.submitList(it)
            }
        }
    }

    override fun removeTask(task: Task) {

        lifecycle.coroutineScope.launch {
            homeViewModel.deleteTask(task)
        }
    }

    private fun setupOnClickListeners() {

        binding?.buttonAdd?.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                .replace(R.id.fragmentContainerView, TaskFragment::class.java, null)
                .addToBackStack(null)
                .commit()
        }
    }

    fun sortTaskByDate() {

        lifecycle.coroutineScope.launch {
            if (sortCheck != true) {
                homeViewModel.sortTaskByDate(1).collect {
                    taskItemAdapter.submitList(it)
                    sortCheck = true
                }
            } else {
                homeViewModel.sortTaskByDate(2).collect {
                    taskItemAdapter.submitList(it)
                    sortCheck = false
                }
            }
        }
    }
}