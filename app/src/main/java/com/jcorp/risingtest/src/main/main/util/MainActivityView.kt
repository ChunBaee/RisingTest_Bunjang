package com.jcorp.risingtest.src.main.main.util

import com.jcorp.risingtest.config.BaseData
import com.jcorp.risingtest.src.main.main.model.*

interface MainActivityView {
    fun onGetDataSuccess (response : CurUserData)

    fun onHomeBannerDataSuccess (response : MainBannerData)

    fun onHomeCategoryDataSuccess (response : HomeCategoryData)

    fun onRecommendDataSuccess (response: MainRecommendRvItem)

    fun onProductDetailDataSuccess (response : ProductDetailData)

    fun onGetBuyProductDataSuccess(response : BuyProductData)

    fun onPostUserAddress (response : BaseData)

    fun onPostBuyData (response : BaseData)
}