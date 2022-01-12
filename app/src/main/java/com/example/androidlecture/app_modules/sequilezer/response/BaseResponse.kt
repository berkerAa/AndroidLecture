package com.example.androidlecture.app_modules.sequilezer.response

data class BaseResponse(
    val status: Int,
    val data: Any = arrayOf<Any>()
)
