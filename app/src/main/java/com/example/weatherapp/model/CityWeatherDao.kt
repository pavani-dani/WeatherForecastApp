package com.example.weatherapp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.CityWeather

@Dao
interface CityWeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cityWeather: CityWeather)

    @Query("SELECT * FROM city_weather_table ORDER BY id DESC")
    fun getAllCityWeather(): LiveData<List<CityWeather>>

    @Query("DELETE FROM city_weather_table")
    suspend fun clearHistory()
}