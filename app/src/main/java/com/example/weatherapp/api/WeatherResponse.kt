package com.example.weatherapp.api

data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val sys: Sys,
    val name: String
)