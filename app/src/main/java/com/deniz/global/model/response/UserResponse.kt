package com.deniz.global.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author: deniz.demirci
 * @date: 19.04.2021
 */

data class UserResponse(
    val path: String,
    @SerializedName("response_code")
    val responseCode: String
)
