package com.yashanand.stayfit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.yashanand.stayfit.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        Handler().postDelayed(Runnable {
            startActivity(Intent(SplashScreen@ this, IntroViewPagerActivity::class.java))
            finish()
        }, 3000)
    }
}