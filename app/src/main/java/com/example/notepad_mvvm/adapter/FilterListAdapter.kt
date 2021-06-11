package com.example.notepad_mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.databinding.FilterItemBinding

class FilterListAdapter(val event: FilterListAdapterEvent) :
    RecyclerView.Adapter<FilterListAdapter.FilterListViewHolder>() {

    inner class FilterListViewHolder(private val binding: FilterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(filterData: FilterData) {
            binding.item = filterData
            binding.root.setOnClickListener {
                event.itemClickEvent(filterData)
            }
            binding.executePendingBindings() // binding이 가끔 멍청해져서 이걸 써주면 정상적으로 반영됨
        }
    }

    private val differ = object : DiffUtil.ItemCallback<FilterData>() {
        override fun areItemsTheSame(oldItem: FilterData, newItem: FilterData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilterData, newItem: FilterData): Boolean {
            return oldItem == newItem
        }
    }

    val list = AsyncListDiffer(this, differ)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FilterListViewHolder(FilterItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FilterListViewHolder, position: Int) {
        holder.bind(list.currentList[position])
    }

    override fun getItemCount(): Int = list.currentList.size
}