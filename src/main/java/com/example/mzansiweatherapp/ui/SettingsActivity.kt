package com.example.mzansiweatherapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.mzansiweatherapp.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setings)

        prefs = getSharedPreferences("mzansi_prefs", MODE_PRIVATE)

        val unitsGroup = findViewById<RadioGroup>(R.id.unitsRadioGroup)
        val cRadio = findViewById<RadioButton>(R.id.radioC)
        val fRadio = findViewById<RadioButton>(R.id.radioF)
        val signOutBtn = findViewById<Button>(R.id.signOutBtn)

        val units = prefs.getString("units", "C")
        if (units == "F") fRadio.isChecked = true else cRadio.isChecked = true

        unitsGroup.setOnCheckedChangeListener { _, checkedId ->
            val chosen = if (checkedId == R.id.radioF) "F" else "C"
            prefs.edit().putString("units", chosen).apply()
        }

        signOutBtn.setOnClickListener {
            prefs.edit().remove("logged_in_user").apply()
            finishAffinity() // exit to launcher (Login is launcher)
        }
    }
}
