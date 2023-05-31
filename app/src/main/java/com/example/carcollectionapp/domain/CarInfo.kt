package com.example.carcollectionapp.domain

data class CarInfo(
    val id: Int,
    val carName: String,
    val picturePath: String,
    val productionDate: String,
    val engineCapacity: Int,
    val insertionDate: String
)