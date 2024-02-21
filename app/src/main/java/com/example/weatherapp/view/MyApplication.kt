package com.example.weatherapp.view

import android.app.Application
import com.example.weatherapp.model.AppDatabase
import com.example.weatherapp.model.CityWeatherRepository
import com.example.weatherapp.model.CityWeatherDao
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