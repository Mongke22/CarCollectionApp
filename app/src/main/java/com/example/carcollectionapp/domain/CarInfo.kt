package com.example.carcollectionapp.domain

data class CarInfo(
    var id: Int,
    var carName: String,
    var picturePath: String,
    var productionDate: String,
    var engineCapacity: Int,
    var insertionDate: String
)
