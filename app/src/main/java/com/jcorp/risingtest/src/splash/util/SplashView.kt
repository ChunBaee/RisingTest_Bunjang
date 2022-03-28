package com.jcorp.risingtest.src.splash.util

import com.jcorp.risingtest.src.splash.model.AutoLoginData

interface SplashView {
    fun onCheckTokenSuccess (response : AutoLoginData)
}