package com.example.weatherapp.model

import androidx.lifecycle.LiveData

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