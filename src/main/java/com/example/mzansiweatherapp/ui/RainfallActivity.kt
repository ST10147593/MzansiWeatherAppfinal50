package com.example.mzansiweatherapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mzansiweatherapp.R
import com.example.mzansiweatherapp.adapters.CityAdapter
import com.example.mzansiweatherapp.data.WeatherRepository
import com.example.mzansiweatherapp.ui.map

private val RainfallActivity.it: Any

class RainfallActivity : AppCompatActivity() {

    private lateinit var rainfallRv: RecyclerView
    private lateinit var titleTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rainfall)

        rainfallRv = findViewById(R.id.rainfallRv)
        titleTv = findViewById(R.id.rainfallTitleTv)

        titleTv.text = "Rainfall probabilities (sample data)"

        val pairs = WeatherRepository.getAllCities()
        // sort by descending rainfall
        val citiesSorted = pairs.map { it.second }.sortedByDescending { it.rainProbabilityPercent }

        rainfallRv.layoutManager = LinearLayoutManager(this)
        rainfallRv.adapter = CityAdapter(citiesSorted) { city ->
            // open detail
            startActivity(android.content.Intent(this, CityDetailActivity::class.java).putExtra("cityName", city.name))
        }
    }
}

private fun Unit.sortedByDescending(function: () -> String) {
    TODO("Not yet implemented")
}

fun WeatherRepository.Companion.getAllCities() {
    TODO("Not yet implemented")
}
