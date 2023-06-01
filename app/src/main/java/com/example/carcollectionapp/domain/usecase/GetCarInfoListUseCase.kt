package com.example.carcollectionapp.domain.usecase

import com.example.carcollectionapp.domain.CarRepository
import javax.inject.Inject

class GetCarInfoListUseCase @Inject constructor(private val repository: CarRepository) {
    operator fun invoke() = repository.getCarInfoList()
}