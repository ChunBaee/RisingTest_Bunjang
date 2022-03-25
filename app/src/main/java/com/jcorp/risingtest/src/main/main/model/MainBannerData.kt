package com.jcorp.risingtest.src.main.main.model

data class MainBannerData(
    val result: List<MainBannerResult>
)
data class MainBannerResult(
    val endDate: String,
    val eventIdx: Int,
    val eventImageUrl: String,
    val eventName: String,
    val eventText: String,
    val startDate: String
)