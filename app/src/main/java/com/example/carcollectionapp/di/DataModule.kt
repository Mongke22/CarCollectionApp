package com.example.carcollectionapp.di

import android.app.Application
import com.example.carcollectionapp.data.CarRepositoryImpl
import com.example.carcollectionapp.data.database.AppDataBase
import com.example.carcollectionapp.data.database.CarInfoDao
import com.example.carcollectionapp.domain.CarRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: CarRepositoryImpl): CarRepository

    companion object{
        @Provides
        fun provideCarInfoDao(application: Application): CarInfoDao{
            return AppDataBase.getInstance(application).carInfoDao()
        }
    }
}