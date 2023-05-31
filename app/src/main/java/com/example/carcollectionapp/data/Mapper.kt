package com.example.carcollectionapp.data

import com.example.carcollectionapp.data.database.CarInfoDbModel
import com.example.carcollectionapp.domain.CarInfo

class Mapper {
    fun mapDbModelToEntity(carDb: CarInfoDbModel): CarInfo {
        return CarInfo(
            id = carDb.id,
            picturePath = carDb.picturePath,
            productionDate = carDb.productionDate,
            engineCapacity = carDb.engineCapacity,
            insertionDate = carDb.insertionDate)
    }

    fun mapEntityToDbModel(car: CarInfo): CarInfoDbModel{
        return CarInfoDbModel(
            id = 0,
            picturePath = car.picturePath,
            productionDate = car.productionDate,
            engineCapacity = car.engineCapacity,
            insertionDate = car.insertionDate)
    }
}