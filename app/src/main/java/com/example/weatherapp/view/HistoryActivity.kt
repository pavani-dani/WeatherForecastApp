package com.example.weatherapp.view

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.InvalidationTracker
import com.example.weatherapp.R
import com.example.weatherapp.model.AppDatabase
import com.example.weatherapp.model.CityWeatherRepository
import com.example.weatherapp.viewmodel.CityWeatherListAdapter
import com.example.weatherapp.viewmodel.CityWeatherViewModel
import com.example.weatherapp.viewmodel.CityWeatherViewModelFactory
import java.text.DecimalFormat

class HistoryActivity : AppCompatActivity() {

    private lateinit var cityWeatherViewModel: CityWeatherViewModel
    private val df = DecimalFormat("#.##")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val clearButton: Button = findViewById(R.id.buttonClearHistory)
        clearButton.setOnClickListener {
            cityWeatherViewModel.clearHistory()
            Toast.makeText(this, "History cleared.", Toast.LENGTH_SHORT).show()
        }

        val cityWeatherDao = AppDatabase.getDatabase(application).cityWeatherDao()
        val cityWeatherRepository = CityWeatherRepository(cityWeatherDao)
        cityWeatherViewModel = ViewModelProvider(
            this,
            CityWeatherViewModelFactory(cityWeatherRepository)
        ).get(CityWeatherViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.historyRecyclerView)
        val adapter = CityWeatherListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        cityWeatherViewModel.getAllCityWeather().observe(this, Observer { cities ->
            cities?.let {
                val sortedCities = it.sortedByDescending { city -> city.id }
                val cityInfoList = sortedCities.map { city ->
                    "${city.cityName}: ${df.format(city.temperature)}°C ${city.weatherDescription}"
                }
                adapter.setCities(cityInfoList)
            }
        })

        /*val backButton: Button = findViewById(R.id.buttonGoBack)
        backButton.setOnClickListener {
            finish()
        }*/
    }
}