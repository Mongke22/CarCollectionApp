package com.example.carcollectionapp.domain.usecase

import com.example.carcollectionapp.domain.CarRepository
import javax.inject.Inject

class GetCarInfoUseCase  @Inject constructor(private val repository: CarRepository) {
    suspend operator fun invoke(carId: Int) = repository.getCarInfo(carId)
}