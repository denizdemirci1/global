package com.deniz.global.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author: deniz.demirci
 * @date: 20.04.2021
 */

data class NextResponse(
    @SerializedName("next_path") val nextPath: String
)
