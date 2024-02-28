package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.repository.CityWeatherRepository

class CityWeatherViewModelFactory(private val repository: CityWeatherRepository) : ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityWeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CityWeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}