package com.jcorp.risingtest.src.main.main.model

data class BuyProductData(
    val result: BuyProductResult
)
data class BuyProductResult(
    val address: String?,
    val detailAddress : String?,
    val name: String?,
    val phone: String?,
    val point: Int,
    val price: Int,
    val productImg: String,
    val title: String,
    val shippingFee : String
)