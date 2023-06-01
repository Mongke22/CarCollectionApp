package com.example.carcollectionapp.di

import com.example.carcollectionapp.domain.CarInfo
import dagger.Module
import dagger.Provides
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Calendar

@Module
class PresentationModule {

    @Provides
    fun provideCarInfo(): CarInfo{
        return CarInfo(id = 0,
            carName = "",
            picturePath = "",
            productionDate = "",
            engineCapacity = 0,
            insertionDate = "")
    }

    @Provides
    fun provideCalendar(): Calendar{
        return Calendar.getInstance()
    }

}