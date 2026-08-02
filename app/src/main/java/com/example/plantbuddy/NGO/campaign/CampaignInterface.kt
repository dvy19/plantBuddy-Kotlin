package com.example.plantbuddy.NGO.campaign

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CampaignInterface {

    @Multipart
    @POST("api/ngo/campaigns/")
    suspend fun createCampaign(

        @Header("Authorization") token: String,

        @Part("title") title: RequestBody,

        @Part("description") description: RequestBody,

        @Part("location") location: RequestBody,

        @Part("start_date") start_date: RequestBody,
        @Part("end_date") end_date: RequestBody,

        @Part("goal_amount") goal_amount: RequestBody,
        @Part("required_volunteers") required_volunteers: RequestBody,

        @Part("is_active") is_active: RequestBody,

        @Part logo: MultipartBody.Part?


    ) : Response<CampaignResponse>
}