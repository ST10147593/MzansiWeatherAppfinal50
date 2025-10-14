package com.example.mzansiweatherapp.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mzansiweatherapp.R

class LoginActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = getSharedPreferences("mzansi_prefs", MODE_PRIVATE)

        val emailEt = findViewById<EditText>(R.id.loginEmailEt)
        val passEt = findViewById<EditText>(R.id.loginPassEt)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val registerTv = findViewById<TextView>(R.id.gotoRegisterTv)

        loginBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val pass = passEt.text.toString().trim()
            val savedPass = prefs.getString("user_$email", null)
            if (savedPass != null && savedPass == pass) {
                // mark logged in
                prefs.edit().putString("logged_in_user", email).apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                passEt.error = "Invalid credentials"
            }
        }

        registerTv.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
