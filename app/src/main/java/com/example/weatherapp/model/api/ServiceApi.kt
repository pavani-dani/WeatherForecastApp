package com.example.weatherapp.model.api

import android.content.Context
import com.example.weatherapp.model.data.AppDatabase
import com.example.weatherapp.model.data.CityWeather
import com.example.weatherapp.model.repository.CityWeatherRepository
import com.example.weatherapp.viewmodel.CityWeatherViewModel
import com.example.weatherapp.viewmodel.CityWeatherViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ServiceApi(private val context: Context) {

    private val url = "https://api.openweathermap.org/"
    private val appid = "b7b0b1f1d6cc3ea4abef9808d3b65589"

    fun getWeather(city: String, onSuccess: (String) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherService::class.java)
        val call = service.getWeather(city, appid)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                response.body()?.let {
                    // get the current date and time
                    val currentDateTime = Calendar.getInstance().time
                    val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    val formattedDateTime = dateTimeFormat.format(currentDateTime)

                    // set the API call date and time in the response
                    it.apiCallDateTime = formattedDateTime

                    val temp = it.main.temp - 273.15
                    val feelsLike = it.main.feels_like - 273.15
                    val output = """Current weather of ${it.name} (${it.sys.country})
 Temp: ${"%.2f".format(temp)} °C
 Feels Like: ${"%.2f".format(feelsLike)} °C
 Humidity: ${it.main.humidity}%
 Description: ${it.weather[0].description}
 Wind Speed: ${it.wind.speed} m/s
 Cloudiness: ${it.clouds.all}%
 Pressure: ${it.main.pressure} hPa"""
                    onSuccess(output)

                    val cityWeatherRepository = CityWeatherRepository(AppDatabase.getDatabase(context).cityWeatherDao())
                    val cityWeatherViewModel = CityWeatherViewModelFactory(cityWeatherRepository).create(CityWeatherViewModel::class.java)
                    cityWeatherViewModel.insert(CityWeather(cityName = it.name, temperature = temp, weatherDescription = it.weather[0].description, apiCallDateTime = formattedDateTime))
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                onSuccess("Error: ${t.message}")
            }
        })
    }
}