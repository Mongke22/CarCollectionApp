package com.example.carcollectionapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.carcollectionapp.data.CarRepositoryImpl
import com.example.carcollectionapp.domain.CarInfo
import com.example.carcollectionapp.domain.usecase.GetCarInfoListUseCase
import com.example.carcollectionapp.domain.usecase.GetCarInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailInfoViewModel(application: Application): AndroidViewModel(application) {

    private val repository = CarRepositoryImpl(application)
    private val getCarInfoUseCase = GetCarInfoUseCase(repository)

    private var _theCar = MutableLiveData<CarInfo>()
    val theCar: LiveData<CarInfo>
        get() = _theCar

    fun getCar(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _theCar.postValue(getCarInfoUseCase.invoke(id))
        }
    }
}