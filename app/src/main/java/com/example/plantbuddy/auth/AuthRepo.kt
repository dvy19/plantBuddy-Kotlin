package com.example.plantbuddy.auth

import com.example.plantbuddy.ApiClient
import retrofit2.Response

class AuthRepo(){

    suspend fun register(request: SignupRequest): Response<SignupResponse> {
        return ApiClient.registerApi.register(request)
    }


    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return ApiClient.loginApi.login(request)
    }

}
