package com.example.androidlecture.app_modules.web_api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("status") val status: Int = 0,
    @Expose(deserialize = false) // deserialize is this filed is not required
    @SerializedName("error") val error: String = "",
    @SerializedName("error_type") val errorType: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: Any?
)
