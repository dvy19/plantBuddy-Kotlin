package com.example.plantbuddy.waterStreak

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface WaterInterface {

    @POST("api/plants/water/")
    suspend fun waterPlant(
        @Header("Authorization") token: String,
        @Body request: WaterLogReq
    ): Response<WaterLogResponse>

    @GET("api/plants/water/{id}")
    suspend fun getDailyWaterLog(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<WaterStreakResponse>
}

