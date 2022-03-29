package com.jcorp.risingtest.src.main.main.model

data class PostBuyData(
    var productId : Int,
    var tradingMethod : String,
    var payMethod : String,
    var addressOption : String?,
    var point : Int,
    var tax : Int
)
