package com.example.androidlecture.app_modules.web_api.response

import com.google.gson.annotations.SerializedName

data class BulkResponse<T>(
    @SerializedName("message") val message: String = "",
    @SerializedName("status") val status: Int = 0,
    @SerializedName("contents") val contents: ArrayList<T>
)
