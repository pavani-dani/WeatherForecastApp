package com.example.weatherapp.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.R
import com.example.weatherapp.model.AppDatabase
import com.example.weatherapp.model.CityWeather
import com.example.weatherapp.model.CityWeatherRepository
import com.example.weatherapp.viewmodel.CityWeatherViewModel
import com.example.weatherapp.viewmodel.CityWeatherViewModelFactory
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var cityInput: EditText
    //private lateinit var countryInput: EditText
    private lateinit var result: TextView
    private lateinit var cityWeatherViewModel: CityWeatherViewModel
    private val url = "https://api.openweathermap.org/data/2.5/weather?q="
    private val appid = "b7b0b1f1d6cc3ea4abef9808d3b65589"
    private val df = DecimalFormat("#.##")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cityInput = findViewById(R.id.cityInput)
        //countryInput = findViewById(R.id.countryInput)
        result = findViewById(R.id.result)

        val cityWeatherRepository = CityWeatherRepository(AppDatabase.getDatabase(application).cityWeatherDao())
        cityWeatherViewModel = ViewModelProvider(
            this,
            CityWeatherViewModelFactory(cityWeatherRepository)
        ).get(CityWeatherViewModel::class.java)

        // Botão Get
        findViewById<Button>(R.id.buttonGet).setOnClickListener {
            getWeather(it)
        }

        // Botão Show History
        findViewById<Button>(R.id.buttonShowHistory).setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        /*Botão Clear History
        findViewById<Button>(R.id.buttonClearHistory).setOnClickListener {
            clearHistory()
        }*/
        val clearButton: Button = findViewById(R.id.buttonClearHistory)
        clearButton.setOnClickListener {
            cityWeatherViewModel.clearHistory()
            Toast.makeText(this, "History cleared.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getWeather(view: View) {
        var tempUrl = ""
        val city = cityInput.text.toString().trim()
        //val country = countryInput.text.toString().trim()

        if (city.isEmpty()) {
            result.text = "City field cannot be empty."
        } else {
            tempUrl = /*if (country.isNotEmpty()) {
                "$url$city,$country&appid=$appid"
            } else {
                */"$url$city&appid=$appid"
            }

            val stringRequest = StringRequest(
                Request.Method.POST,
                tempUrl,
                { response ->
                    var output = ""
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
                        //result.setTextColor(Color.rgb(68, 134, 199))
                        output = """Current weather of $cityName ($countryName)
 Temp: ${df.format(temp)} °C
 Feels Like: ${df.format(feelsLike)} °C
 Humidity: $humidity%
 Description: $description
 Wind Speed: ${wind}m/s (meters per second)
 Cloudiness: $clouds%
 Pressure: $pressure hPa"""
                        result.text = output
                        cityWeatherViewModel.insert(CityWeather(cityName = cityName, temperature = temp, weatherDescription = description))
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                { error ->
                    Toast.makeText(applicationContext, error.toString().trim(), Toast.LENGTH_SHORT).show()
                }
            )

            Volley.newRequestQueue(this).add(stringRequest)
        }
    }

    /*private fun showHistory() {
        cityWeatherViewModel.getAllCityWeather().observe(this, { cities ->
            if (cities.isNotEmpty()) {
                val historyStringBuilder = StringBuilder()
                for (city in cities) {
                    historyStringBuilder.append("${city.cityName}: ${city.weatherDescription}\n")
                }
                result.text = historyStringBuilder.toString()
            } else {
                result.text = "No history available."
            }
        })
    }*/

    /*private fun clearHistory() {
        cityWeatherViewModel.clearHistory()
        result.text = "History cleared."
    }
}*/