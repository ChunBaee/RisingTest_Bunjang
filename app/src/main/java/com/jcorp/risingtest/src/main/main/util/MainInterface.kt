package com.jcorp.risingtest.src.main.main.util

import com.jcorp.risingtest.config.BaseData
import com.jcorp.risingtest.src.main.main.model.*
import com.jcorp.risingtest.src.main.upload.model.UploadMyProductData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MainInterface {
    @GET("app/users")
    fun getData() : Call<CurUserData>

    @GET("app/events")
    fun getHomeBanners() : Call<MainBannerData>

    @GET("app/homecategories")
    fun getHomeCategories() : Call<HomeCategoryData>

    @GET("app/products")
    fun getRecommendItemData() : Call<MainRecommendRvItem>

    @GET("app/products/{productId}")
    fun getProductDetailData(
        @Path (value = "productId", encoded = true) productId : Int
    ) : Call<ProductDetailData>

    @GET("app/orders/{productId}")
    fun getBuyProductData(
        @Path(value = "productId", encoded = true) productId: Int
    ) : Call<BuyProductData>

    @POST("app/addresses/delivery")
    fun postUserAddrData (
        @Body uploadUserAddrData : PostUserAddrData
    ) : Call<BaseData>

    @POST("app/orders")
    fun postBuyData(
        @Body postBuyData : PostBuyData
    ) : Call<BaseData>
}