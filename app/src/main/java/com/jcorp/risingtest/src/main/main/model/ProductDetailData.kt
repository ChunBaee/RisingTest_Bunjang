package com.jcorp.risingtest.src.main.main.model

data class ProductDetailData(
    val result: ProductDetailResult
)

data class ProductDetailResult(
    val productIdx: Int,
    val productImgList: List<ProductImg>,
    val productInfo: ProductInfo,
    val productTagList: List<ProductTag>,
    val relateProductList: List<RelateProduct>,
    val reviewList: List<Any>,
    val sellProductList: List<SellProduct>,
    val storeInfo: StoreInfo
)
data class ProductImg(
    val productImgId: Int,
    val productImgUrl: String
)
data class ProductInfo(
    val category: String,
    val categoryId: Int,
    val createdAt: String,
    val directAddress: String,
    val explanation: String,
    val favoriteCount: String,
    val myFavorite: String,
    val price: Int,
    val productInquiry: String,
    val productOption: String,
    val securePayment: String,
    val sellStatus: String,
    val title: String
)
data class ProductTag(
    val tagId: Int,
    val tagName: String
)
data class SellProduct(
    val price: Int,
    val productIdx: Int,
    val productImgUrl: String
)
data class RelateProduct(
    val price: Int,
    val productIdx: Int,
    val productImgUrl: String,
    val title: String
)
data class StoreInfo(
    val followerCount: Int,
    val reviewCount: Int,
    val starRate: Double,
    val storeId: Int,
    val storeName: String
)