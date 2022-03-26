package com.jcorp.risingtest.src.main.upload.util

import com.jcorp.risingtest.config.BaseData
import com.jcorp.risingtest.src.main.upload.model.UploadCategoryData

interface UploadCategoryView {
    fun onGetLargeCategorySuccess (response : UploadCategoryData)

    fun onGetMiddleCategorySuccess (response : UploadCategoryData)

    fun onGetSmallCategorySuccess (response : UploadCategoryData)

    fun onUploadUserProductSuccess (response : BaseData)
}