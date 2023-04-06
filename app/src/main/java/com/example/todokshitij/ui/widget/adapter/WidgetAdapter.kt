package com.example.todokshitij.ui.widget.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todokshitij.ui.widget.data.model.PersonalizationSequence
import com.example.todokshitij.databinding.WidgetItemBinding

class WidgetAdapter()
    : RecyclerView.Adapter<WidgetAdapter.MyViewHolder>() {

    private val widgetList : ArrayList<PersonalizationSequence> = arrayListOf()

    class MyViewHolder(var binding : WidgetItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindView(widget: PersonalizationSequence){

            binding.tVWidgetItemName.text = widget.widgetName
            binding.tVWidgetItemId.text = widget.widgetId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = WidgetItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = widgetList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindView(widgetList[position])
    }

    fun setList(list: ArrayList<PersonalizationSequence>?) {
        widgetList.clear()
        widgetList.addAll(list!!)
        notifyDataSetChanged()
    }
}