package com.example.mzansiweatherapp.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.mzansiweatherapp.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        prefs = getSharedPreferences("mzansi_prefs", MODE_PRIVATE)

        val emailEt = findViewById<EditText>(R.id.regEmailEt)
        val passEt = findViewById<EditText>(R.id.regPassEt)
        val registerBtn = findViewById<Button>(R.id.regBtn)

        registerBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val pass = passEt.text.toString().trim()
            if (email.isEmpty() || pass.length < 4) {
                if (email.isEmpty()) emailEt.error = "Enter email"
                if (pass.length < 4) passEt.error = "Password min 4"
                return@setOnClickListener
            }
            // Store credentials (simple approach)
            prefs.edit().putString("user_$email", pass).apply()
            prefs.edit().putString("logged_in_user", email).apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
