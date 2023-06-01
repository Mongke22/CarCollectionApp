package com.example.carcollectionapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.carcollectionapp.data.database.CarInfoDao
import com.example.carcollectionapp.domain.CarInfo
import com.example.carcollectionapp.domain.CarRepository
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(
    private val carInfoDao: CarInfoDao,
    private val mapper: Mapper
    ): CarRepository {

    override fun getCarInfoList(): LiveData<List<CarInfo>> {
        return Transformations.map(carInfoDao.getCarsInfoList()) {
            it.map { carInfoDb ->
                mapper.mapDbModelToEntity(carInfoDb)
            }
        }
    }

    override suspend fun getCarInfo(carId: Int): CarInfo {
        return mapper.mapDbModelToEntity(carInfoDao.getCarInfo(carId))
    }

    override suspend fun addCarInfo(car: CarInfo) {
        carInfoDao.insertCarInfo(mapper.mapEntityToDbModel(car))
    }
}