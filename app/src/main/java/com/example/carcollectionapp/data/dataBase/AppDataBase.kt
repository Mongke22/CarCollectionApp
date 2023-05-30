package com.example.carcollectionapp.data.dataBase

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CarInfoDbModel::class],
    version = 0, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    companion object {

        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "CarCollection.db"
        private val LOCK = Any()

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        application,
                        AppDatabase::class.java,
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