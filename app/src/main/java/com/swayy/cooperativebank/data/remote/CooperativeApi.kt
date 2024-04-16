package com.swayy.cooperativebank.data.remote

import com.swayy.cooperativebank.data.remote.dto.LoginResponseDto
import com.swayy.cooperativebank.domain.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CooperativeApi {

    //post request
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponseDto
}