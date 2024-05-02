package com.example.data_visuals

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Using Handler to delay the screen transition
        Handler().postDelayed({
            // Start your main activity after the splash time out
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // close this activity
        }, SPLASH_TIME_OUT)
    }
}
