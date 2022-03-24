package com.jcorp.risingtest.src.main.main.util

import com.jcorp.risingtest.src.main.main.model.CurUserData
import com.jcorp.risingtest.src.main.main.model.MainRecommendRvItem
import com.jcorp.risingtest.src.main.main.model.RecommendRvData
import retrofit2.Call
import retrofit2.http.GET

interface MainInterface {
    @GET("/app/users")
    fun getData() : Call<CurUserData>

    @GET("app/products")
    fun getRecommendItemData() : Call<MainRecommendRvItem>
}