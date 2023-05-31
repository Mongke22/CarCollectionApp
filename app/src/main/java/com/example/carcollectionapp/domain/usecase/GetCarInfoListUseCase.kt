package com.example.carcollectionapp.domain.usecase

import com.example.carcollectionapp.domain.CarRepository

class GetCarInfoListUseCase(private val repository: CarRepository) {
    operator fun invoke() = repository.getCarInfoList()
}