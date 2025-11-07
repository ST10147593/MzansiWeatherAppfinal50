package com.example.mzansiweatherapp.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mzansiweatherapp.R
import java.util.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setings)

        prefs = getSharedPreferences("mzansi_prefs", MODE_PRIVATE)

        val darkModeSwitch = findViewById<Switch>(R.id.darkModeSwitch)
        val signOutBtn = findViewById<Button>(R.id.signOutBtn)
        val languageSpinner = findViewById<Spinner>(R.id.languageSpinner)

        // Dark mode initial state
        val savedMode = prefs.getString("theme_mode", "SYSTEM")
        darkModeSwitch.isChecked = savedMode == "DARK"

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                prefs.edit().putString("theme_mode", "DARK").apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                prefs.edit().putString("theme_mode", "LIGHT").apply()
            }
        }

        // Language spinner
        val languages = listOf("English", "isiZulu", "Afrikaans")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter

        val selectedLang = prefs.getString("app_lang", "en")
        languageSpinner.setSelection(
            when (selectedLang) {
                "zu" -> 1
                "af" -> 2
                else -> 0
            }
        )

        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val langCode = when (position) {
                    1 -> "zu"
                    2 -> "af"
                    else -> "en"
                }
                prefs.edit().putString("app_lang", langCode).apply()
                setLocale(langCode)
                // restart the activity stack to apply language change
                startActivity(Intent(this@SettingsActivity, MainActivity::class.java))
                finishAffinity()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        signOutBtn.setOnClickListener {
            prefs.edit().remove("logged_in_user").apply()
            finishAffinity()
        }
    }

    private fun setLocale(langCode: String) {
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
