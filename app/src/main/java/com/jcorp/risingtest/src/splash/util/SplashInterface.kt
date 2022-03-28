package com.jcorp.risingtest.src.splash.util

import com.jcorp.risingtest.src.splash.model.AutoLoginData
import retrofit2.Call
import retrofit2.http.GET

interface SplashInterface {

    @GET("app/users")
    fun checkAutoLogin() : Call<AutoLoginData>
}