package com.example.weatherapp.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_weather_table")
data class CityWeather(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cityName: String,
    val temperature: Double,
    val weatherDescription: String,
    val apiCallDateTime: String
)