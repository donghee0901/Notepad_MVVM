package com.example.notepad_mvvm.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.databinding.ActivityMainBinding
import com.example.notepad_mvvm.databinding.TagAddItemBinding
import com.example.notepad_mvvm.databinding.TagItemBinding
import java.util.zip.Inflater

class SelectFilterAdapter(val event: SelectFilterAdapterEvent) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val ADD_FILTER = 1
    private val VIEW_FILTER = 2

    inner class SelectFilterViewHolder(val binding : TagItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(filterData: FilterData){
            binding.data = filterData
            binding.root.setOnClickListener {
                event.itemClickEvent(filterData)
            }
            binding.executePendingBindings()
        }
    }

    inner class AddFilterViewHolder(val binding : TagAddItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(){
            binding.root.setOnClickListener {
                event.addItemClickEvent()
            }
            binding.executePendingBindings()
        }
    }

    private val differ = object : DiffUtil.ItemCallback<FilterData>(){
        override fun areItemsTheSame(oldItem: FilterData, newItem: FilterData): Boolean {
            Log.d("Differ", "areItemsTheSame: $oldItem, $newItem")
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilterData, newItem: FilterData): Boolean {
            Log.d("Differ", "areContentsTheSame: $oldItem, $newItem")
            return oldItem == newItem
        }

    }

    val list = AsyncListDiffer(this, differ)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if(viewType == VIEW_FILTER){
            SelectFilterViewHolder(TagItemBinding.inflate(inflater, parent, false))
        }
        else{
            AddFilterViewHolder(TagAddItemBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder)
        {
            is SelectFilterViewHolder -> holder.bind(list.currentList[position])
            is AddFilterViewHolder -> holder.bind()
        }
    }

    override fun getItemCount() = list.currentList.size

    override fun getItemViewType(position: Int): Int {
        return if(list.currentList[position].id == -1) ADD_FILTER
        else VIEW_FILTER
    }
}