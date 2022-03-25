package com.jcorp.risingtest.src.main.main.util

import com.jcorp.risingtest.src.main.main.model.*
import retrofit2.Call
import retrofit2.http.GET

interface MainInterface {
    @GET("app/users")
    fun getData() : Call<CurUserData>

    @GET("app/events")
    fun getHomeBanners() : Call<MainBannerData>

    @GET("app/homecategories")
    fun getHomeCategories() : Call<HomeCategoryData>

    @GET("app/products")
    fun getRecommendItemData() : Call<MainRecommendRvItem>
}