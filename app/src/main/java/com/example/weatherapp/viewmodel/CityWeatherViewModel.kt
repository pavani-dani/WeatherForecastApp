package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.data.CityWeather
import com.example.weatherapp.model.repository.CityWeatherRepository
import kotlinx.coroutines.launch

class CityWeatherViewModel(private val repository: CityWeatherRepository) : ViewModel() {

    fun insert(cityWeather: CityWeather) {
        viewModelScope.launch {
            repository.insert(cityWeather)
        }
    }

    fun getAllCityWeather() = repository.getAllCityWeather()

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }

    class Factory(private val repository: CityWeatherRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CityWeatherViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CityWeatherViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}