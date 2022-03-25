package com.jcorp.risingtest.src.main.main.util

import com.jcorp.risingtest.src.main.main.model.CurUserData
import com.jcorp.risingtest.src.main.main.model.HomeCategoryData
import com.jcorp.risingtest.src.main.main.model.MainBannerData
import com.jcorp.risingtest.src.main.main.model.MainRecommendRvItem

interface MainActivityView {
    fun onGetDataSuccess (response : CurUserData)

    fun onHomeBannerDataSuccess (response : MainBannerData)

    fun onHomeCategoryDataSuccess (response : HomeCategoryData)

    fun onRecommendDataSuccess (response: MainRecommendRvItem)
}