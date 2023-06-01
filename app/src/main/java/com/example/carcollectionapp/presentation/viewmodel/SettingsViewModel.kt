package com.example.carcollectionapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.carcollectionapp.data.SettingsStorage
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val storage: SettingsStorage
): ViewModel() {

    fun resetSettings(navController: NavController){
        viewModelScope.launch {
            storage.saveSettings(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toString())
            storage.saveAddCount(2)
            storage.saveViewCount(3)
        }
        navController.popBackStack()
    }
}