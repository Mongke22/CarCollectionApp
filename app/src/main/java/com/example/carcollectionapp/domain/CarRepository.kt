package com.example.carcollectionapp.domain

import androidx.lifecycle.LiveData

interface CarRepository {

    fun getCarInfoList(): LiveData<List<CarInfo>>

    suspend fun getCarInfo(carId: Int): CarInfo

    suspend fun addCarInfo(car: CarInfo)
}