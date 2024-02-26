package com.example.weatherapp.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.model.AppDatabase
import com.example.weatherapp.model.CityWeather
import com.example.weatherapp.model.CityWeatherRepository
import com.example.weatherapp.viewmodel.CityWeatherViewModel
import com.example.weatherapp.viewmodel.CityWeatherViewModelFactory
import org.json.JSONException
import org.json.JSONObject

class ServiceApi(private val context: Context) {

    private val url = "https://api.openweathermap.org/data/2.5/weather?q="
    private val appid = "b7b0b1f1d6cc3ea4abef9808d3b65589"

    fun getWeather(city: String, onSuccess: (String) -> Unit) {
        val tempUrl = "$url$city&appid=$appid"
        val stringRequest = StringRequest(
            Request.Method.GET,
            tempUrl,
            Response.Listener<String> { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val jsonArray = jsonResponse.getJSONArray("weather")
                    val jsonObjectWeather = jsonArray.getJSONObject(0)
                    val description = jsonObjectWeather.getString("description")
                    val jsonObjectMain = jsonResponse.getJSONObject("main")
                    val temp = jsonObjectMain.getDouble("temp") - 273.15
                    val feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15
                    val pressure = jsonObjectMain.getInt("pressure").toFloat()
                    val humidity = jsonObjectMain.getInt("humidity")
                    val jsonObjectWind = jsonResponse.getJSONObject("wind")
                    val wind = jsonObjectWind.getString("speed")
                    val jsonObjectClouds = jsonResponse.getJSONObject("clouds")
                    val clouds = jsonObjectClouds.getString("all")
                    val jsonObjectSys = jsonResponse.getJSONObject("sys")
                    val countryName = jsonObjectSys.getString("country")
                    val cityName = jsonResponse.getString("name")
                    val output = """Current weather of $cityName ($countryName)
 Temp: ${"%.2f".format(temp)} °C
 Feels Like: ${"%.2f".format(feelsLike)} °C
 Humidity: $humidity%
 Description: $description
 Wind Speed: $wind m/s
 Cloudiness: $clouds%
 Pressure: $pressure hPa"""
                    onSuccess(output)

                    val cityWeatherRepository = CityWeatherRepository(AppDatabase.getDatabase(context).cityWeatherDao())
                    val cityWeatherViewModel = CityWeatherViewModelFactory(cityWeatherRepository).create(CityWeatherViewModel::class.java)
                    cityWeatherViewModel.insert(CityWeather(cityName = cityName, temperature = temp, weatherDescription = description))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                onSuccess("Error: ${error.message}")
            }
        )

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }
}