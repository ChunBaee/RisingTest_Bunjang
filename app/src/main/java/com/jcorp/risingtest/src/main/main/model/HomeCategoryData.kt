package com.jcorp.risingtest.src.main.main.model

data class HomeCategoryData(
    val result: List<HomeCategoryResult>
)

data class HomeCategoryResult(
    val category: List<HomeCategory>,
    val iconType: String
)

data class HomeCategory(
    val categoryLargeIdx: Int,
    val categoryLargeName: String,
    val iconImageUrl: String
)