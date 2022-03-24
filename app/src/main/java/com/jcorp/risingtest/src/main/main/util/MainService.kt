package com.jcorp.risingtest.src.main.main.util

import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.src.main.main.model.CurUserData
import com.jcorp.risingtest.src.main.main.model.MainRecommendRvItem
import com.jcorp.risingtest.src.main.main.model.RecommendRvData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainService (val view : MainActivityView) {
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
}