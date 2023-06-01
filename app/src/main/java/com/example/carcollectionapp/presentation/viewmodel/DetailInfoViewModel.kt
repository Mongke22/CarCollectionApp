package com.example.carcollectionapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carcollectionapp.domain.CarInfo
import com.example.carcollectionapp.domain.usecase.GetCarInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailInfoViewModel @Inject constructor(
    private val getCarInfoUseCase: GetCarInfoUseCase
): ViewModel() {

    private var _theCar = MutableLiveData<CarInfo>()
    val theCar: LiveData<CarInfo>
        get() = _theCar

    fun getCar(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _theCar.postValue(getCarInfoUseCase.invoke(id))
        }
    }
}