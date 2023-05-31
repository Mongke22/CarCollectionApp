package com.example.carcollectionapp.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CarInfoDbModel::class],
    version = 1, exportSchema = false
)

abstract class AppDataBase : RoomDatabase() {
    companion object {

        private var INSTANCE: AppDataBase? = null
        private const val DB_NAME = "CarCollection.db"
        private val LOCK = Any()

        fun getInstance(application: Application): AppDataBase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        application,
                        AppDataBase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun carInfoDao(): CarInfoDao
}