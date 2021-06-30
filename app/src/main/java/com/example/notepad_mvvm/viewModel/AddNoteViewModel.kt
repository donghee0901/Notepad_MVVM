
package com.example.notepad_mvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.dataClass.NoteData
import com.example.notepad_mvvm.database.NoteRepository
import kotlinx.coroutines.launch

class AddNoteViewModel(private val noteRepository: NoteRepository, data : NoteData?) : ViewModel(){
    private var isUpdate = false
    private val addFilterButton = FilterData("추가버튼", -1, -1)
    var formatted = NoteData("", "", arrayListOf(addFilterButton))
    val _title = MutableLiveData<String>()
    val _context = MutableLiveData<String>()

    private val _selectFilter = MutableLiveData<ArrayList<FilterData>>()
    val selectFilter: LiveData<ArrayList<FilterData>>
        get() = _selectFilter

    private var currentData = arrayListOf(addFilterButton)

    init {
        data?.let{
            this.formatted = it
            currentData = ArrayList(it.filter)
            currentData.add(addFilterButton)
            isUpdate = true
        }
        _title.value = formatted.title
        _context.value = formatted.context
        _selectFilter.value = ArrayList(currentData)
    }

//    fun addFilter(data : FilterData){
//        filter.add(data)
//        filter = filter.distinctBy { it.id } as ArrayList<FilterData>
//    }
//
//    fun deleteFilter(data : FilterData){
//        filter.remove(data)
//    }

    fun saveNoteData() = viewModelScope.launch {
        currentData.removeAt(currentData.lastIndex)
        val data = NoteData(_title.value!!, _context.value!!, currentData.toList())
        if(isUpdate){
            data.id = formatted.id
            noteRepository.updateNote(data)
        }else{
            noteRepository.insertNote(data)
        }
    }

    fun deleteSelectFilter(data: FilterData) {
        currentData.remove(data)
        _selectFilter.value = ArrayList(currentData)
    }

    fun addSelectFilter(data: FilterData) {
        currentData.removeAt(currentData.lastIndex)
        currentData.addAll(arrayListOf(data, addFilterButton))
        currentData = ArrayList(currentData.distinctBy { it.id })

        _selectFilter.value = ArrayList(currentData)
    }
}