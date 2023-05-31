package com.example.carcollectionapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.carcollectionapp.data.CarRepositoryImpl
import com.example.carcollectionapp.domain.CarInfo
import com.example.carcollectionapp.domain.usecase.GetCarInfoListUseCase

class CarsListViewModel(application: Application): AndroidViewModel(application) {

    private val repository = CarRepositoryImpl(application)
    private val getCarInfoListUseCase = GetCarInfoListUseCase(repository)

    private var _carList = getCarInfoListUseCase()
    val carList: LiveData<List<CarInfo>>
        get() = _carList

    fun showCarDetailInfo(id: Int){

    }

    fun moveToAddNewCarScreen(){

    }

}