package com.example.carcollectionapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class CarInfoDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val carName: String,
    val picturePath: String,
    val productionDate: String,
    val engineCapacity: Int,
    val insertionDate: String
)
