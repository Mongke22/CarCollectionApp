package com.example.carcollectionapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.carcollectionapp.R
import com.example.carcollectionapp.data.CarRepositoryImpl
import com.example.carcollectionapp.domain.CarInfo
import com.example.carcollectionapp.domain.usecase.AddCarInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewCarViewModel(application: Application): AndroidViewModel(application) {

    private val repository = CarRepositoryImpl(application)
    private val addCarInfoUseCase = AddCarInfoUseCase(repository)

    fun saveNewCar(car: CarInfo, navController: NavController){
        viewModelScope.launch(Dispatchers.IO){
            addCarInfoUseCase.invoke(car)
        }
        navController.popBackStack()
    }
}