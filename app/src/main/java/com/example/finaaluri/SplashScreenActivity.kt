package com.example.finaaluri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASHSCREEN_TIME: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
           val intent = Intent(this, RegisterActivity::class.java)
           startActivity(intent)
           finish()
        }, SPLASHSCREEN_TIME)
    }
}