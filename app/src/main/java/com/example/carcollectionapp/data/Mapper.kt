package com.example.carcollectionapp.data

import com.example.carcollectionapp.data.database.CarInfoDbModel
import com.example.carcollectionapp.domain.CarInfo
import javax.inject.Inject

class Mapper @Inject constructor(){
    fun mapDbModelToEntity(carDb: CarInfoDbModel): CarInfo {
        return CarInfo(
            id = carDb.id,
            carName = carDb.carName,
            picturePath = carDb.picturePath,
            productionDate = carDb.productionDate,
            engineCapacity = carDb.engineCapacity,
            insertionDate = carDb.insertionDate)
    }

    fun mapEntityToDbModel(car: CarInfo): CarInfoDbModel{
        return CarInfoDbModel(
            id = 0,
            carName = car.carName,
            picturePath = car.picturePath,
            productionDate = car.productionDate,
            engineCapacity = car.engineCapacity,
            insertionDate = car.insertionDate)
    }
}