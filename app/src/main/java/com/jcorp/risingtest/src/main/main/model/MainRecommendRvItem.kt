package com.jcorp.risingtest.src.main.main.model

data class MainRecommendRvItem(
    var productPhoto: String? = "",
    var isHeartClicked: Boolean? = false,
    var price: String? = "",
    var productName: String? = "",
    var location: String? = "",
    var time: String? = "",
    var isThunderPay: Boolean? = false,
    var heartCount: Int? = 0
)
