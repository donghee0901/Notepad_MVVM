package com.example.notepad_mvvm.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad_mvvm.dataClass.NoteData
import com.example.notepad_mvvm.databinding.NoteItemBinding

class NoteListAdapter(val event: NoteListAdapterEvent) : RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder>() {

    inner class NoteListViewHolder(private val binding : NoteItemBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        val adapter = NoteFilterListAdapter()
        fun bind(data : NoteData) {
            binding.data = data
            binding.noteFilter.adapter = adapter
            adapter.list.submitList(data.filter)
            binding.root.setOnClickListener {
                event.itemClickEvent(data)
            }
            binding.root.setOnLongClickListener {
                makeDialog(data)
                true
            }
        }

        private fun makeDialog(data: NoteData) {
            val myDialog = AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_Dialog)

            myDialog.setMessage("메모를 삭제하시겠습니까?")
                .setTitle("삭제")
                .setPositiveButton("예") { _, _ ->
                    event.itemDeleteEvent(data)
                }
                .setNegativeButton("아니오") { _, _ ->

                }
                .show()
        }
    }

    val differ = object : DiffUtil.ItemCallback<NoteData>() {
        override fun areItemsTheSame(oldItem: NoteData, newItem: NoteData): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NoteData, newItem: NoteData): Boolean = oldItem == newItem
    }

    val list = AsyncListDiffer(this, differ)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteListViewHolder(NoteItemBinding.inflate(inflater, parent, false), parent.context)
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        holder.bind(list.currentList[position])
    }

    override fun getItemCount(): Int = list.currentList.size
}