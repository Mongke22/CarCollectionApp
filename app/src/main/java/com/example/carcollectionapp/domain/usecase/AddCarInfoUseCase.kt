package com.example.carcollectionapp.domain.usecase

import com.example.carcollectionapp.domain.CarInfo
import com.example.carcollectionapp.domain.CarRepository

class AddCarInfoUseCase(private val repository: CarRepository) {
    suspend operator fun invoke(car: CarInfo) = repository.addCarInfo(car)
}