package com.example.plantbuddy.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthInterface {

    @POST("api/accounts/register/")
    suspend fun register(@Body request: SignupRequest): Response<SignupResponse>

    @POST("api/accounts/login/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>



}