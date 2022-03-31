package com.jcorp.risingtest.src.main.upload.util

import com.jcorp.risingtest.config.BaseData
import com.jcorp.risingtest.src.main.upload.model.UploadCategoryData
import com.jcorp.risingtest.src.main.upload.model.UploadLocationData

interface UploadCategoryView {
    fun onGetLargeCategorySuccess (response : UploadCategoryData)

    fun onGetMiddleCategorySuccess (response : UploadCategoryData)

    fun onGetLocationData (response : UploadLocationData)

    fun onGetSmallCategorySuccess (response : UploadCategoryData)

    fun onUploadUserProductSuccess (response : BaseData)
}