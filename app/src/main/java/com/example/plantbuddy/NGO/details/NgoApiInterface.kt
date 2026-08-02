package com.example.plantbuddy.NGO.details

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NgoApiInterface {

    @Multipart
    @POST("api/ngo/create/")
    suspend fun createNGO(

        @Header("Authorization") token: String,

        @Part("name") name: RequestBody,

        @Part("description") description: RequestBody,

        @Part("phone_number") phone: RequestBody,

        @Part("city") city: RequestBody,

        @Part("address") address: RequestBody,

        @Part("website") website: RequestBody,

        @Part logo: MultipartBody.Part?

    ): Response<NgoDetailsResponse>


}