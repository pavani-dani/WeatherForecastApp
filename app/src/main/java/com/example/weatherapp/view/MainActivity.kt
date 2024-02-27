package com.example.weatherapp.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.api.ServiceApi
import com.example.weatherapp.model.AppDatabase
import com.example.weatherapp.model.CityWeatherRepository
import com.example.weatherapp.viewmodel.CityWeatherViewModel
import com.example.weatherapp.viewmodel.CityWeatherViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var cityInput: EditText
    private lateinit var result: TextView
    private lateinit var cityWeatherViewModel: CityWeatherViewModel
    private var emptyMessage = "City field cannot be empty."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cityInput = findViewById(R.id.cityInput)
        result = findViewById(R.id.result)

        val cityWeatherRepository = CityWeatherRepository(AppDatabase.getDatabase(application).cityWeatherDao())
        cityWeatherViewModel = ViewModelProvider(
            this,
            CityWeatherViewModelFactory(cityWeatherRepository)
        )[CityWeatherViewModel::class.java]

        // Get
        findViewById<Button>(R.id.buttonGet).setOnClickListener {
            getWeather()
        }

        // Show History
        findViewById<Button>(R.id.buttonShowHistory).setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getWeather() {
        val city = cityInput.text.toString().trim()

        if (city.isEmpty()) {
            result.text = emptyMessage
        } else {
            val serviceApi = ServiceApi(this)
            serviceApi.getWeather(city) { response ->
                runOnUiThread {
                    result.setTextColor(Color.rgb(0, 0, 0))
                    result.text = response
                }
            }
        }
    }
}