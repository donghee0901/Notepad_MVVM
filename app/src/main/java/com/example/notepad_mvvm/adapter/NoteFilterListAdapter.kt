package com.example.notepad_mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.databinding.NoteColorItemBinding

class NoteFilterListAdapter : RecyclerView.Adapter<NoteFilterListAdapter.FilterViewHolder>() {
    inner class FilterViewHolder(private val binding: NoteColorItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: FilterData) {
            binding.color = data.tagColor
        }
    }

    val differ = object : DiffUtil.ItemCallback<FilterData>(){
        override fun areItemsTheSame(oldItem: FilterData, newItem: FilterData): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FilterData, newItem: FilterData): Boolean = oldItem == newItem

    }

    val list = AsyncListDiffer(this, differ)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FilterViewHolder(NoteColorItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(list.currentList[position])
    }

    override fun getItemCount(): Int = list.currentList.size

}