package com.jcorp.risingtest.src.main.upload.util

import com.jcorp.risingtest.src.main.upload.model.UploadCategoryData

interface UploadActivityView {
    fun onGetLargeCategorySuccess (response : UploadCategoryData)

    fun onGetMiddleCategorySuccess (response : UploadCategoryData)

    fun onGetSmallCategorySuccess (response : UploadCategoryData)
}