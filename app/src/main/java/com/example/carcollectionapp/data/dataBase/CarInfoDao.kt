package com.example.carcollectionapp.data.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CarInfoDao {
    @Query("SELECT * FROM cars")
    fun getCarsInfoList(): LiveData<List<CarInfoDbModel>>

    @Query("SELECT * FROM cars WHERE id == :getId LIMIT 1")
    fun getCarInfo(getId: Int): CarInfoDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCarInfo(car: CarInfoDbModel)
}