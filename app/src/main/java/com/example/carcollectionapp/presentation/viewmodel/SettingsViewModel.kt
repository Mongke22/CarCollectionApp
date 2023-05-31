package com.example.carcollectionapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.carcollectionapp.R
import com.example.carcollectionapp.data.SettingsStorage
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application): AndroidViewModel(application) {

    private val storage = SettingsStorage(application)

    fun resetSettings(navController: NavController){
        viewModelScope.launch {
            storage.saveSettings(false)
            storage.saveAddCount(2)
            storage.saveViewCount(3)
        }
        navController.popBackStack()
    }
}