package com.example.todokshitij.ui.widget.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todokshitij.data.api.ApiHelper
import com.example.todokshitij.data.api.RetrofitClient
import com.example.todokshitij.databinding.ActivityWidgetBinding
import com.example.todokshitij.ui.widget.adapter.WidgetAdapter
import com.example.todokshitij.ui.widget.viewmodel.WidgetViewModel
import com.example.todokshitij.ui.widget.viewmodel.WidgetViewModelFactory
import com.example.todokshitij.utils.Status

class WidgetActivity : AppCompatActivity() {

    private var binding: ActivityWidgetBinding? = null
    private var widgetAdapter: WidgetAdapter? = null
    private var widgetViewModel: WidgetViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWidgetBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupViewModel()
        setupRecyclerView()
        //getApiResponse()
        getApiResponse()
    }

    private fun setupViewModel() {
        widgetViewModel = ViewModelProvider(
            this,
            WidgetViewModelFactory(apiHelper = ApiHelper(RetrofitClient.apiService))
        )[WidgetViewModel::class.java]
    }

    private fun setupRecyclerView() {

        widgetAdapter = WidgetAdapter()
        binding?.recyclerViewWidget?.apply {
            layoutManager = LinearLayoutManager(this@WidgetActivity, RecyclerView.VERTICAL, false)
            adapter = widgetAdapter
        }
    }

//    private fun getApiResponse() {
//
//        lifecycleScope.launch {
//            try {
//
//            } catch (e: Exception) {
//                Toast.makeText(this@WidgetActivity, e.toString(), Toast.LENGTH_LONG).show()
//            }
//
//        }
//    }

    private fun getApiResponse() {

        widgetViewModel?.getWidgetData()?.observe(this){

            it.let { resource->

                when (resource.status) {
                    Status.SUCCESS -> {
                        binding?.recyclerViewWidget?.visibility = View.VISIBLE
                            resource.data?.body()?.let { res ->
                                (binding?.recyclerViewWidget?.adapter as? WidgetAdapter)
                                    ?.setList(res.personalizationSequence)
                        }
                    }
                    Status.ERROR -> {
                        binding?.recyclerViewWidget?.visibility = View.VISIBLE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding?.recyclerViewWidget?.visibility = View.GONE
                    }
                }
            }
        }
    }
}