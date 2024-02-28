package com.example.weatherapp.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.CityWeather

class CityWeatherListAdapter : RecyclerView.Adapter<CityWeatherListAdapter.CityWeatherViewHolder>() {

    private var cityNames: List<String> = listOf()

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

    /*class CityWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cityName: String) {
            itemView.findViewById<TextView>(R.id.item_text).text = cityName
        }
    }*/

    class CityWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewCityWeather: TextView = itemView.findViewById(R.id.item_text)

        fun bind(cityWeather: String) {
            textViewCityWeather.text = cityWeather
        }
    }
}