package com.example.notepad_mvvm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notepad_mvvm.viewModel.MainActivityViewModel
import com.example.notepad_mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        // if viewModel doesn't have any custom constructor, you can use this -> ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel

        binding.addMemoButton.setOnClickListener {
            startActivity(Intent(this, FilterListActivity::class.java))
        }
    }
}