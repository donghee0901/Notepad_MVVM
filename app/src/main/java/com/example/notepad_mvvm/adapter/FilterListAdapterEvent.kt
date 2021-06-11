package com.example.notepad_mvvm.adapter

import com.example.notepad_mvvm.dataClass.FilterData

interface FilterListAdapterEvent {
    fun itemClickEvent(filterData: FilterData)
}