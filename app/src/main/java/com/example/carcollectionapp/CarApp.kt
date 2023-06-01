package com.example.carcollectionapp

import android.app.Application
import com.example.carcollectionapp.di.DaggerApplicationComponent

class CarApp: Application() {
    val component by lazy{
        DaggerApplicationComponent.factory().create(this)
    }
}