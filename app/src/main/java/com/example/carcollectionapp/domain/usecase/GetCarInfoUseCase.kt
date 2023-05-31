package com.example.carcollectionapp.domain.usecase

import com.example.carcollectionapp.domain.CarRepository

class GetCarInfoUseCase(private val repository: CarRepository) {
    suspend operator fun invoke(carId: Int) = repository.getCarInfo(carId)
}