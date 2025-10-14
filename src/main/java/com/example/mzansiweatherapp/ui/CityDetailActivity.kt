package com.example.mzansiweatherapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mzansiweatherapp.R
import com.example.mzansiweatherapp.data.WeatherRepository

class CityDetailActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_detail)
        prefs = getSharedPreferences("mzansi_prefs", MODE_PRIVATE)

        val cityName = intent.getStringExtra("cityName") ?: return
        val c = WeatherRepository.searchCities(cityName).firstOrNull()?.second
            ?: WeatherRepository.findCityByName(cityName)

        val cityNameTv = findViewById<TextView>(R.id.cityDetailNameTv)
        val tempTv = findViewById<TextView>(R.id.cityDetailTempTv)
        val rainTv = findViewById<TextView>(R.id.cityDetailRainTv)

        if (c != null) {
            cityNameTv.text = c.name
            val units = prefs.getString("units", "C")
            if (units == "F") {
                val f = c.currentTempC * 9/5 + 32
                tempTv.text = String.format("%.1f °F", f)
            } else {
                tempTv.text = String.format("%.1f °C", c.currentTempC)
            }
            rainTv.text = "${c.rainProbabilityPercent} % chance of rain"
        } else {
            cityNameTv.text = "City not found"
            tempTv.text = "-"
            rainTv.text = "-"
        }
    }
}
