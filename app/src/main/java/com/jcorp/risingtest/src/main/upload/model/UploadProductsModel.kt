package com.jcorp.risingtest.src.main.upload.model

data class UploadProductsModel(
    val productImgList : List<productImgUrl>,
    val title : String,
    val categoryLarge : Int,
    val categoryMiddle : Int,
    val categorySmall : Int,
    val price : Int,
    val productTagList : List<tagName>,
    val explanation : String,
    val shippingFee : String,
    val quantity : Int,
    val productStatus : String,
    val exchangePossible : String,
    val securePayment : String

)
data class productImgUrl (
    val productImgUrl : String
        )

data class tagName (
    val tagName : String
        )
