package com.example.weatherapp.model.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather")
    fun getWeather(@Query("q") city: String, @Query("appid") appid: String): Call<WeatherResponse>
}