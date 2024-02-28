package com.example.weatherapp.model.api

data class Weather(val description: String)
data class Main(val temp: Double, val feels_like: Double, val pressure: Float, val humidity: Int)
data class Wind(val speed: String)
data class Clouds(val all: String)
data class Sys(val country: String)