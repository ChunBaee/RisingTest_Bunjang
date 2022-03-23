package com.jcorp.risingtest.src.main.login.model

data class LoginData(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)

data class Result(
    val jwt: String,
    val userIdx: Int
)