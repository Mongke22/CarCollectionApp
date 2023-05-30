package com.example.carcollectionapp.data.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class CarInfoDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val picturePath: String,
    val productionDate: String,
    val engineCapacity: Int,
    val insertionDate: String
)
