package com.example.weatherapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CityWeather::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityWeatherDao(): CityWeatherDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration() // migração de versões db
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}