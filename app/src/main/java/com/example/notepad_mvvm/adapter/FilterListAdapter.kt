package com.example.notepad_mvvm.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.databinding.FilterItemBinding

class FilterListAdapter(private val list: ArrayList<FilterData>) :
    RecyclerView.Adapter<FilterListAdapter.FilterListViewHolder>() {
    inner class FilterListViewHolder(private val binding: FilterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(filterData: FilterData) {
            binding.item = filterData
            binding.executePendingBindings()
        }
    }

    fun setList(list: List<FilterData>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
    fun removeItem(position: Int, callback : (FilterData)->Unit) {
        val deleteData = list[position]
        callback(deleteData)
        list.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FilterListViewHolder(FilterItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FilterListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}