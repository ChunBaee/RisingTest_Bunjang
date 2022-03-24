package com.jcorp.risingtest.src.main.main.util

import com.jcorp.risingtest.src.main.main.model.CurUserData
import com.jcorp.risingtest.src.main.main.model.MainRecommendRvItem

interface MainActivityView {
    fun onGetDataSuccess (response : CurUserData)

    fun onRecommendDataSuccess (response: MainRecommendRvItem)
}