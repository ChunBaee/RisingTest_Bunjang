package com.jcorp.risingtest.src.splash.model

data class AutoLoginData(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)

data class Result(
    val email: String,
    val phone: String,
    val shopName: String,
    val userIdx: Int,
    val userName: String
)