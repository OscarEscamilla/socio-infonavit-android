package com.example.infonavit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import com.example.infonavit.MainActivity
import com.example.infonavit.R
import com.example.infonavit.ui.login.LoginActivity
import com.example.infonavit.utils.UserPreferences

class SplashActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        timer()

    }

    fun timer(){
        val SPLASH_TIME_OUT: Long = 3000
        Handler().postDelayed({

            val userPreferences = UserPreferences(applicationContext)

            if(!TextUtils.isEmpty(userPreferences.getToken())){

                startActivity(Intent(applicationContext, MainActivity::class.java))
            }else {

                startActivity(Intent(applicationContext, LoginActivity::class.java))

            }
            finish()
        }, SPLASH_TIME_OUT)

    }
}