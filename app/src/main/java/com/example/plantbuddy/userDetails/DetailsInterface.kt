package com.example.plantbuddy.userDetails

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.Part


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


    @Multipart
    @POST("api/accounts/create-volunteer-profile/")
    suspend fun createVolunteerProfile(
        @Header("Authorization") token: String,

        @Part("name") name :RequestBody,

        @Part("phone") phone :RequestBody,

        @Part("city") city :RequestBody,

        @Part("gender") gender :RequestBody,

        @Part image: MultipartBody.Part?

    ): Response<VolunteerProfileResponse>




}