package com.example.carcollectionapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.carcollectionapp.R
import com.example.carcollectionapp.data.CarRepositoryImpl
import com.example.carcollectionapp.domain.CarInfo
import com.example.carcollectionapp.domain.CarRepository
import com.example.carcollectionapp.domain.usecase.AddCarInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewCarViewModel @Inject constructor(
    private val addCarInfoUseCase: AddCarInfoUseCase
): ViewModel() {

    fun saveNewCar(car: CarInfo, navController: NavController){
        viewModelScope.launch(Dispatchers.IO){
            addCarInfoUseCase.invoke(car)
        }
        navController.popBackStack()
    }
}