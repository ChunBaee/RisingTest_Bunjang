package com.jcorp.risingtest.src.main.upload.model

data class UploadCategoryData (
    val result : List<CategoryData>
)

data class CategoryData (
        val categoryIdx : Int,
        val categoryUrl : String,
        val categoryName : String
        )