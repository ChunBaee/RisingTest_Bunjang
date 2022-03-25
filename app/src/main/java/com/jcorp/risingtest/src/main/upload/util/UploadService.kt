package com.jcorp.risingtest.src.main.upload.util

import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.src.main.upload.model.UploadCategoryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadService (val view : UploadActivityView) {
    private val mRetrofitInterface = ApplicationClass.mRetrofit.create(UploadInterface::class.java)

    fun getLargeCategory() {
        mRetrofitInterface.getLargeCategory().enqueue(object : Callback<UploadCategoryData> {
            override fun onResponse(
                call: Call<UploadCategoryData>,
                response: Response<UploadCategoryData>
            ) {
                view.onGetLargeCategorySuccess(response.body() as UploadCategoryData)
            }
            override fun onFailure(call: Call<UploadCategoryData>, t: Throwable) {
            }
        })
    }

    fun getMiddleCategory(largeCategoryIdx : Int) {
        mRetrofitInterface.getMiddleCategory(largeCategoryIdx).enqueue(object : Callback<UploadCategoryData> {
            override fun onResponse(
                call: Call<UploadCategoryData>,
                response: Response<UploadCategoryData>
            ) {
                view.onGetMiddleCategorySuccess(response.body() as UploadCategoryData)
            }
            override fun onFailure(call: Call<UploadCategoryData>, t: Throwable) {
            }
        })
    }

    fun getSmallCategory (middleCategoryIdx : Int) {
        mRetrofitInterface.getSmallCategory(middleCategoryIdx).enqueue(object : Callback<UploadCategoryData> {
            override fun onResponse(
                call: Call<UploadCategoryData>,
                response: Response<UploadCategoryData>
            ) {
                view.onGetSmallCategorySuccess(response.body() as UploadCategoryData)
            }
            override fun onFailure(call: Call<UploadCategoryData>, t: Throwable) {
            }

        })
    }
}