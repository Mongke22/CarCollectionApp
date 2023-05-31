package com.example.carcollectionapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.carcollectionapp.data.database.AppDataBase
import com.example.carcollectionapp.domain.CarInfo
import com.example.carcollectionapp.domain.CarRepository

class CarRepositoryImpl(private val application: Application): CarRepository {

    private val carInfoDao = AppDataBase.getInstance(application).carInfoDao()
    private val mapper = Mapper()


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