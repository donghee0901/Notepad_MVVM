package com.example.notepad_mvvm

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad_mvvm.adapter.FilterListAdapter

object ItemTouchHelperManager {
    fun RecyclerView.registerSwiper(callback: (viewHolder: RecyclerView.ViewHolder, direction: Int) -> Unit) {
        val helper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                callback.invoke(viewHolder, direction)
            }
        })
        // ItemTouchHelper에 RecyclerView 설정
        helper.attachToRecyclerView(this)
//        attachToRecyclerView(binding.addFilterView)
    }
}