package com.example.weatherapp.view

import android.app.Application
import com.example.weatherapp.model.data.AppDatabase
import com.example.weatherapp.model.repository.CityWeatherRepository
import com.example.weatherapp.model.repository.CityWeatherDao
import com.example.weatherapp.viewmodel.CityWeatherViewModelFactory

class MyApplication : Application() {

    val cityWeatherDao: CityWeatherDao by lazy {
        AppDatabase.getDatabase(this).cityWeatherDao()
    }

    val cityWeatherRepository: CityWeatherRepository by lazy {
        CityWeatherRepository(cityWeatherDao)
    }

    val cityWeatherViewModelFactory: CityWeatherViewModelFactory by lazy {
        CityWeatherViewModelFactory(cityWeatherRepository)
    }
}