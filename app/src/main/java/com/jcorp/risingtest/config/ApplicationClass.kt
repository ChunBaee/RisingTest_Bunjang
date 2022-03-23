package com.jcorp.risingtest.config

import android.app.Application
import android.content.SharedPreferences
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    val API_URL = "https://isileeserver.shop"
    //소셜로그인 추가

    companion object {
        lateinit var mLoginSharedPreferences : SharedPreferences
        lateinit var mChatSharedPreferences : SharedPreferences

        lateinit var fbStorage : StorageReference

        lateinit var mRetrofit : Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        mLoginSharedPreferences = applicationContext.getSharedPreferences("X-ACCESS-TOKEN", MODE_PRIVATE)
        fbStorage = FirebaseStorage.getInstance().reference
        initRetrofitInstance()

    }
    private fun initRetrofitInstance() {
        val client : OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(XAccessTokenInterceptor())
            .build()

        mRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}