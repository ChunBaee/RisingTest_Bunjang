package com.jcorp.risingtest.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.jcorp.risingtest.MainActivity
import com.jcorp.risingtest.config.BaseActivity
import com.jcorp.risingtest.databinding.ActivitySplashBinding
import com.jcorp.risingtest.src.main.login.LoginActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 1500)
    }

    // 로그인 API가 올 경우, Preference에 토큰값이 있는지 확인 후 넘어가기!
    // 있을경우 -> MainActivity / 없을경우 -> LoginActivity
}
