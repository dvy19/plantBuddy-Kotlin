package com.example.plantbuddy.userDetails

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.GET


interface DetailsInterface {

    @POST("api/accounts/create-profile/")
    suspend fun createProfile(
        @Header("Authorization") token: String,
        @Body request: UserDetailReq
    ): Response<UserDetailResponse>

    @GET("api/accounts/create-profile/")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): Response<UserDetailResponse>



}