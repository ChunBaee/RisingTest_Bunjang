package com.jcorp.risingtest.src.main.login.util

import android.util.Log
import com.jcorp.risingtest.config.ApplicationClass
import com.jcorp.risingtest.src.main.login.model.ChangeShopNameData
import com.jcorp.risingtest.src.main.login.model.LoginData
import com.jcorp.risingtest.src.main.login.model.UserData
import com.jcorp.risingtest.src.main.login.model.UserShopNameData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService (val view : LoginActivityView) {
    private val mRetrofitInterface = ApplicationClass.mRetrofit.create(LoginInterface::class.java)

    fun trySignIn(userName : String, email : String, password : String, phone : String) {
        mRetrofitInterface.signIn(UserData(userName, email, password, phone)).enqueue(object : Callback<LoginData> {
            override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                Log.d("----", "onResponse: ${response.body()?.code}")
                view.onGetDataSuccess(response.body() as LoginData)
            }

            override fun onFailure(call: Call<LoginData>, t: Throwable) {
                Log.d("----", "onFailure: ${t.cause}")
            }

        })
    }

    fun tryChangeShopName(shopName : String) {
        mRetrofitInterface.putShopName(UserShopNameData(shopName)).enqueue(object : Callback<ChangeShopNameData>{
            override fun onResponse(
                call: Call<ChangeShopNameData>,
                response: Response<ChangeShopNameData>
            ) {
                view.onChangeShopNameSuccess(response.body() as ChangeShopNameData)
            }

            override fun onFailure(call: Call<ChangeShopNameData>, t: Throwable) {
                Log.d("----", "onFailure: ${t.cause}")
            }

        })
    }
}