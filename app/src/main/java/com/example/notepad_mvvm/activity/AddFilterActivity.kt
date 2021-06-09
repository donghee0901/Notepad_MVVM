package com.example.notepad_mvvm.activity

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.notepad_mvvm.R
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.database.FilterDao
import com.example.notepad_mvvm.database.FilterDatabase
import com.example.notepad_mvvm.database.FilterRepository
import com.example.notepad_mvvm.databinding.ActivityAddFilterBinding
import com.example.notepad_mvvm.viewModel.AddFilterActivityViewModel
import com.example.notepad_mvvm.viewModelFactory.AddFilterActivityViewModelFactory
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape

class AddFilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFilterBinding
    private lateinit var viewModel: AddFilterActivityViewModel
    var stroke: GradientDrawable? = null

    companion object {
        @JvmStatic
        @BindingAdapter("color_background")
        fun setBackground(view: View?, color: Int) {
            view?.background?.setTint(color)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_filter)
        val dao : FilterDao = FilterDatabase.getInstance(application).getFilterDao()
        val repository = FilterRepository(dao)
        val factory = AddFilterActivityViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(AddFilterActivityViewModel::class.java)
        binding.addFilterViewModel = viewModel
        binding.lifecycleOwner = this

        binding.apply {
            stroke = addFilterStroke.background as GradientDrawable
            stroke?.setStroke(20, viewModel.filterColor.value!!)
            saveFilterButton.contentBackground?.setTint(viewModel.filterColor.value!!)

            selectColor.setOnClickListener {
                ColorPickerDialog
                    .Builder(this@AddFilterActivity)                        // Pass Activity Instance
                    .setTitle("태그 색 선택")            // Default "Choose Color"
                    .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
                    .setDefaultColor("#ffffff")     // Pass Default Color
                    .setColorListener { color, colorHex ->
                        it.background.setTint(color)
                        selectColorHex.text = colorHex
                        viewModel.changeFilterColor(color)
                        stroke?.setStroke(20, color)
                        saveFilterButton.background.setTint(color)
                    }
                    .show()
            }

            saveFilterButton.setOnClickListener {
                viewModel.saveFilterData()
                finish()
            }
        }
    }
}