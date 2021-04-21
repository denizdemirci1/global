package com.deniz.global.data.service

import com.deniz.global.model.response.NextResponse
import com.deniz.global.model.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author: deniz.demirci
 * @date: 19.04.2021
 */

interface Service {

    companion object {
        const val BASE_URL = "http://10.0.2.2:8000"
    }

    @GET("{path}")
    suspend fun fetchUser(
        @Path("path") path: String
    ): UserResponse

    @GET(".")
    suspend fun fetchNextPath(): NextResponse

}
