package com.example.weatherapp.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R

class CityWeatherListAdapter : RecyclerView.Adapter<CityWeatherListAdapter.CityWeatherViewHolder>() {

    private var cityNames: List<String> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setCities(cities: List<String>) {
        cityNames = cities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city_weather, parent, false)
        return CityWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityWeatherViewHolder, position: Int) {
        holder.bind(cityNames[position])
    }

    override fun getItemCount(): Int {
        return cityNames.size
    }

    class CityWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewCityWeather: TextView = itemView.findViewById(R.id.item_text)

        fun bind(cityWeather: String) {
            textViewCityWeather.text = cityWeather
        }
    }
}