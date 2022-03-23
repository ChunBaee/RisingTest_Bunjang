package com.jcorp.risingtest.src.main.login

import com.jcorp.risingtest.src.main.login.model.ChangeShopNameData
import com.jcorp.risingtest.src.main.login.model.LoginData
import com.jcorp.risingtest.src.main.login.model.UserData
import com.jcorp.risingtest.src.main.login.model.UserShopNameData
import retrofit2.Call
import retrofit2.http.*

interface LoginInterface {

    @POST("/app/users")
    fun signIn(
        @Body userData : UserData
    ) : Call<LoginData>

    @POST("/app/users/shopname")
    fun putShopName(
        @Body shopName : UserShopNameData
    ) : Call<ChangeShopNameData>
}