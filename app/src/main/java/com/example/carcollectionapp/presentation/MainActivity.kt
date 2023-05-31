package com.example.carcollectionapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carcollectionapp.R
import com.example.carcollectionapp.presentation.fragments.CarsListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}