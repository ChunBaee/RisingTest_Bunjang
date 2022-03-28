package com.jcorp.risingtest.src.splash.util

import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.src.splash.model.AutoLoginData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashService(val view : SplashView) {
    private val mRetrofitInterface = ApplicationClass.mRetrofit.create(SplashInterface::class.java)

    fun getAutoLoginData() {
        mRetrofitInterface.checkAutoLogin().enqueue(object : Callback<AutoLoginData> {
            override fun onResponse(call: Call<AutoLoginData>, response: Response<AutoLoginData>) {
                view.onCheckTokenSuccess(response.body() as AutoLoginData)
            }
            override fun onFailure(call: Call<AutoLoginData>, t: Throwable) {
            }

        })
    }
}