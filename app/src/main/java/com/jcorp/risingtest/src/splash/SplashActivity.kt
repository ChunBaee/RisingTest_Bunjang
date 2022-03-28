package com.jcorp.risingtest.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.config.BaseActivity
import com.jcorp.risingtest.databinding.ActivitySplashBinding
import com.jcorp.risingtest.src.main.login.LoginActivity
import com.jcorp.risingtest.src.main.main.MainActivity
import com.jcorp.risingtest.src.splash.model.AutoLoginData
import com.jcorp.risingtest.src.splash.util.SplashService
import com.jcorp.risingtest.src.splash.util.SplashView

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate),
    SplashView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 1500)
        SplashService(this).getAutoLoginData()
    }

    override fun onCheckTokenSuccess(response: AutoLoginData) {
        when(response.code) {
            1000 -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
