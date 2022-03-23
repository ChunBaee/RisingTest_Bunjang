package com.jcorp.risingtest.src.main.login.adapter

import com.jcorp.risingtest.src.main.login.model.ChangeShopNameData
import com.jcorp.risingtest.src.main.login.model.LoginData

interface LoginActivityView {
    fun onGetDataSuccess(response : LoginData)

    fun onChangeShopNameSuccess (response : ChangeShopNameData)
}