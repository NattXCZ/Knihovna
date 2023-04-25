package com.example.knihovna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.knihovna.databinding.ActivityMainBinding
import com.example.knihovna.viewModels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val model: MainActivityViewModel by viewModels {
        MainActivityViewModel.MyFactory((application as MyApplication))

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.model = model




    }

}

