package com.example.mzansiweatherapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mzansiweatherapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import androidx.core.content.edit

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // added to strings via google-services
            .requestEmail()
            .build()
        googleClient = GoogleSignIn.getClient(this, gso)

        val emailEt = findViewById<EditText>(R.id.loginEmailEt)
        val passEt = findViewById<EditText>(R.id.loginPassEt)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val registerTv = findViewById<TextView>(R.id.gotoRegisterTv)
        val googleBtn = findViewById<Button>(R.id.googleSignInBtn)

        loginBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val pass = passEt.text.toString().trim()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // local fallback - still store credentials in SharedPreferences if used previously
                val prefs = getSharedPreferences("mzansi_prefs", MODE_PRIVATE)
                val savedPass = prefs.getString("user_$email", null)
                if (savedPass != null && savedPass == pass) {
                    prefs.edit().putString("logged_in_user", email).apply()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    passEt.error = "Invalid credentials"
                }
            }
        }

        registerTv.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        googleBtn.setOnClickListener {
            // launch google sign-in
            val signInIntent = googleClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(Exception::class.java)
            account?.idToken?.let { idToken ->
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) { authResult ->
                        if (authResult.isSuccessful) {
                            // Signed in
                            val user = auth.currentUser
                            // store email in prefs
                            user?.email?.let { email ->
                                getSharedPreferences("mzansi_prefs", MODE_PRIVATE)
                                    .edit { putString("logged_in_user", email) }
                            }
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            // auth failed
                            Log.w("LoginActivity", "signInWithCredential:failure", authResult.exception)
                        }
                    }
            }
        } catch (e: Exception) {
            Log.e("LoginActivity", "Google sign-in failed", e)
        }
    }
}
