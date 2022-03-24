package com.jcorp.risingtest.src.main.main.model

data class MainRecommendRvItem(
    val result : List<RecommendRvData>
)
data class RecommendRvData(
    val productIdx : Int,
    val productImg : String,
    val title : String,
    val price : Int,
    val directAddress : String,
    val securePayment : String,
    val myFavorite : String,
    val createdAt : String,
    val favoriteCount : String
)
