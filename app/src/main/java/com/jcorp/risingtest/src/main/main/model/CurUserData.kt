package com.jcorp.risingtest.src.main.main.model

data class CurUserData(
    val result : Result
)

data class Result (
    val userIdx : Int,
    val userName : String,
    val shopName : String,
    val email : String,
    val phone : String
        )