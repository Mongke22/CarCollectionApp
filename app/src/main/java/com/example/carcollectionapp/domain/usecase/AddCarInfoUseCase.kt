package com.example.carcollectionapp.domain.usecase

import com.example.carcollectionapp.domain.CarInfo
import com.example.carcollectionapp.domain.CarRepository
import javax.inject.Inject

class AddCarInfoUseCase @Inject constructor(private val repository: CarRepository) {
    suspend operator fun invoke(car: CarInfo) = repository.addCarInfo(car)
}