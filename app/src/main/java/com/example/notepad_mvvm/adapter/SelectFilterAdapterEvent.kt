package com.example.notepad_mvvm.adapter

import com.example.notepad_mvvm.dataClass.FilterData


interface SelectFilterAdapterEvent {
    fun itemClickEvent(filterData: FilterData)
    fun addItemClickEvent()
}