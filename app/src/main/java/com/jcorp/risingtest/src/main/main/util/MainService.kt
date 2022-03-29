package com.jcorp.risingtest.src.main.main.util

import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.config.BaseData
import com.jcorp.risingtest.src.main.main.MainProductBuyFragment
import com.jcorp.risingtest.src.main.main.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainService(val view: MainActivityView) {
    private val mRetrofitInterface = ApplicationClass.mRetrofit.create(MainInterface::class.java)

    fun getCurUserData() {
        mRetrofitInterface.getData().enqueue(object : Callback<CurUserData> {
            override fun onResponse(call: Call<CurUserData>, response: Response<CurUserData>) {
                view.onGetDataSuccess(response.body() as CurUserData)
            }
            override fun onFailure(call: Call<CurUserData>, t: Throwable) {
            }
        })
    }

    fun getRecommendRvData() {
        mRetrofitInterface.getRecommendItemData().enqueue(object : Callback<MainRecommendRvItem> {
            override fun onResponse(
                call: Call<MainRecommendRvItem>,
                response: Response<MainRecommendRvItem>
            ) {
                view.onRecommendDataSuccess(response.body() as MainRecommendRvItem)
            }
            override fun onFailure(call: Call<MainRecommendRvItem>, t: Throwable) {
            }
        })
    }

    fun getHomeCategoryData() {
        mRetrofitInterface.getHomeCategories().enqueue(object : Callback<HomeCategoryData> {
            override fun onResponse(
                call: Call<HomeCategoryData>,
                response: Response<HomeCategoryData>
            ) {
                view.onHomeCategoryDataSuccess(response.body() as HomeCategoryData)
            }
            override fun onFailure(call: Call<HomeCategoryData>, t: Throwable) {
            }

        })
    }

    fun getMainBannerData() {
        mRetrofitInterface.getHomeBanners().enqueue(object : Callback<MainBannerData> {
            override fun onResponse(
                call: Call<MainBannerData>,
                response: Response<MainBannerData>
            ) {
                view.onHomeBannerDataSuccess(response.body() as MainBannerData)
            }
            override fun onFailure(call: Call<MainBannerData>, t: Throwable) {
            }

        })
    }

    fun getProductDetailData(productId : Int) {
        mRetrofitInterface.getProductDetailData(productId).enqueue(object : Callback<ProductDetailData> {
            override fun onResponse(
                call: Call<ProductDetailData>,
                response: Response<ProductDetailData>
            ) {
                view.onProductDetailDataSuccess(response.body() as ProductDetailData)
            }
            override fun onFailure(call: Call<ProductDetailData>, t: Throwable) {
            }

        })
    }

    fun getBuyProductData(productId : Int) {
        mRetrofitInterface.getBuyProductData(productId).enqueue(object : Callback<BuyProductData> {
            override fun onResponse(
                call: Call<BuyProductData>,
                response: Response<BuyProductData>
            ) {
                view.onGetBuyProductDataSuccess(response.body() as BuyProductData)
            }
            override fun onFailure(call: Call<BuyProductData>, t: Throwable) {
            }

        })
    }

    fun postUserAddrData(addr : PostUserAddrData) {
        mRetrofitInterface.postUserAddrData(addr).enqueue(object : Callback<BaseData> {
            override fun onResponse(call: Call<BaseData>, response: Response<BaseData>) {
                view.onPostUserAddress(response.body() as BaseData)
            }
            override fun onFailure(call: Call<BaseData>, t: Throwable) {
            }
        })
    }

    fun postBuyData (buyData : PostBuyData) {
        mRetrofitInterface.postBuyData(buyData).enqueue(object : Callback<BaseData> {
            override fun onResponse(call: Call<BaseData>, response: Response<BaseData>) {
                view.onPostBuyData(response.body() as BaseData)
            }
            override fun onFailure(call: Call<BaseData>, t: Throwable) {
            }
        })
    }
}