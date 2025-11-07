package com.example.mzansiweatherapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mzansiweatherapp.R
import com.example.mzansiweatherapp.adapters.CityAdapter
import com.example.mzansiweatherapp.data.WeatherRepository

val View.second: Any
val ProvinceActivity.it: Any

class ProvinceActivity : AppCompatActivity() {

    private lateinit var citiesRv: RecyclerView
    private lateinit var titleTv: TextView
    private lateinit var searchEt: EditText
    private lateinit var searchBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_province)

        titleTv = findViewById(R.id.provinceTitleTv)
        citiesRv = findViewById(R.id.citiesRv)
        searchEt = findViewById(R.id.provinceSearchEt)
        searchBtn = findViewById(R.id.provinceSearchBtn)

        val provName = intent.getStringExtra("provinceName")
        val query = intent.getStringExtra("searchQuery")

        val provinces = WeatherRepository.getProvinces()

        if (!provName.isNullOrEmpty()) {
            titleTv.text = provName
            val province = provinces.find { it.name == provName }
            showCities(province?.cities ?: emptyList())
        } else if (!query.isNullOrEmpty()) {
            titleTv.text = "Search results for \"$query\""
            val pairs = WeatherRepository.searchCities(query)
            val cities = pairs.map { it.second }
            showCities(cities)
        } else {
            titleTv.text = "Provinces"
            showCities(emptyList())
        }

        searchBtn.setOnClickListener {
            val q = searchEt.text.toString().trim()
            if (q.isNotEmpty()) {
                val pairs = WeatherRepository.searchCities(q)
                val cities = pairs.map { it.second }
                titleTv.text = "Search results for \"$q\""
                showCities(cities)
            } else {
                searchEt.error = "Enter search term"
            }
        }
    }

    private fun showCities(cities: Unit) {
        citiesRv.layoutManager = LinearLayoutManager(this)
        citiesRv.adapter = CityAdapter(cities) { city ->
            val i = Intent(this, CityDetailActivity::class.java)
            i.putExtra("cityName", city.name)
            startActivity(i)
        }
    }
}

fun Unit.map(function: () -> Any) {
    TODO("Not yet implemented")
}

private fun Unit.find(function: () -> Boolean) {
    TODO("Not yet implemented")
}
