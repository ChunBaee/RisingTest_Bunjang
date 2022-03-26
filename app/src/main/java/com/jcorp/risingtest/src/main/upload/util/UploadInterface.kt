package com.jcorp.risingtest.src.main.upload.util

import com.jcorp.risingtest.config.BaseData
import com.jcorp.risingtest.src.main.upload.model.UploadCategoryData
import com.jcorp.risingtest.src.main.upload.model.UploadMyProductData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UploadInterface {

    @GET("/app/products/largeCategory")
    fun getLargeCategory() : Call<UploadCategoryData>

    @GET("/app/products/middleCategory/{largeCategoryId}")
    fun getMiddleCategory(
        @Path(value = "largeCategoryId", encoded = true) largeCategoryId : Int
    ) : Call<UploadCategoryData>

    @GET("/app/products/smallCategory/{middleCategoryId}")
    fun getSmallCategory (
        @Path (value = "middleCategoryId", encoded = true) middleCategoryId : Int
    ) : Call<UploadCategoryData>

    @POST("app/products")
    fun onPostMyProduct(
        @Body uploadUserProduct : UploadMyProductData
    ) : Call<BaseData>
}