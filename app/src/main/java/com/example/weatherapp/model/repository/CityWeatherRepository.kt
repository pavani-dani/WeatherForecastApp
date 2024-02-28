package com.example.weatherapp.model.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.model.data.CityWeather

class CityWeatherRepository(private val cityWeatherDao: CityWeatherDao) {

    suspend fun insert(cityWeather: CityWeather) {
        cityWeatherDao.insert(cityWeather)
    }

    fun getAllCityWeather(): LiveData<List<CityWeather>> {
        return cityWeatherDao.getAllCityWeather()
    }

    suspend fun clearHistory() {
        cityWeatherDao.clearHistory()
    }
}