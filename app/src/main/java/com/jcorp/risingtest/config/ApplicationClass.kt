package com.jcorp.risingtest.config

import android.app.Application
import android.content.SharedPreferences

class ApplicationClass : Application() {
    val LOGIN_API = "" //일반 로그인 api 주소
    //소셜로그인 추가

    companion object {
        lateinit var mSharedPreferences : SharedPreferences

    }

    override fun onCreate() {
        super.onCreate()

    }
}