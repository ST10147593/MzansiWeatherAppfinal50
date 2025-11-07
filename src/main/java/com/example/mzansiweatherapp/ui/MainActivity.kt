package com.example.mzansiweatherapp.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mzansiweatherapp.R
import com.example.mzansiweatherapp.adapters.ProvinceAdapter
import com.example.mzansiweatherapp.data.WeatherRepository
import com.example.mzansiweatherapp.models.Province
import androidx.work.*
import com.example.mzansiweatherapp.sync.SyncWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var provincesRv: RecyclerView
    private lateinit var searchEt: EditText
    private lateinit var rainfallBtn: Button
    private lateinit var settingsBtn: ImageButton
    private lateinit var welcomeTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//This makes the app sync every 15 minutes when connected.
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "mzansi_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )

        prefs = getSharedPreferences("mzansi_prefs", MODE_PRIVATE)

        provincesRv = findViewById(R.id.provincesRv)
        searchEt = findViewById(R.id.searchEt)
        rainfallBtn = findViewById(R.id.rainfallBtn)
        settingsBtn = findViewById(R.id.settingsBtn)
        welcomeTv = findViewById(R.id.welcomeTv)

        val logged = prefs.getString("logged_in_user", null)
        welcomeTv.text = "Welcome, ${logged ?: "Guest"}"

        val provinces = WeatherRepository.getProvinces()
        provincesRv.layoutManager = LinearLayoutManager(this)
        provincesRv.adapter = ProvinceAdapter(provinces) { p -> openProvince(p) }

        findViewById<Button>(R.id.searchBtn).setOnClickListener {
            val q = searchEt.text.toString().trim()
            if (q.isNotEmpty()) {
                // First try exact city -> open detail
                val city = WeatherRepository.findCityByName(q)
                if (city != null) {
                    val i = Intent(this, CityDetailActivity::class.java)
                    i.putExtra("cityName", city.name)
                    startActivity(i)
                } else {
                    // fallback: search fuzzy, show province list of matches
                    val i = Intent(this, ProvinceActivity::class.java)
                    i.putExtra("searchQuery", q)
                    startActivity(i)
                }
            } else {
                searchEt.error = "Enter a city or province"
            }
        }

        rainfallBtn.setOnClickListener {
            startActivity(Intent(this, RainfallActivity::class.java))
        }

        settingsBtn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun openProvince(province: Province) {
        val i = Intent(this, ProvinceActivity::class.java)
        i.putExtra("provinceName", province.name)
        startActivity(i)
    }
}

fun WeatherRepository.Companion.getProvinces() {
    TODO("Not yet implemented")
}
