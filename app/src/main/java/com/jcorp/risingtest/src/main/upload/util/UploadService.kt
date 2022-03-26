package com.jcorp.risingtest.src.main.upload.util

import android.util.Log
import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.config.BaseData
import com.jcorp.risingtest.src.main.upload.model.UploadCategoryData
import com.jcorp.risingtest.src.main.upload.model.UploadMyProductData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadService (val view : UploadCategoryView) {
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

    fun uploadMyProduct(uploadData : UploadMyProductData) {
        mRetrofitInterface.onPostMyProduct(uploadData).enqueue(object : Callback<BaseData> {
            override fun onResponse(call: Call<BaseData>, response: Response<BaseData>) {
                view.onUploadUserProductSuccess(response.body() as BaseData)
            }
            override fun onFailure(call: Call<BaseData>, t: Throwable) {
                Log.d("0000", "onFailure: ${t.message}")
            }

        })
    }
}