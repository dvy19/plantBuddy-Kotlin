package com.example.plantbuddy.waterStreak

import com.example.plantbuddy.ApiClient
import com.example.plantbuddy.FAQRetrofit.FaqRequest
import com.example.plantbuddy.auth.SessionManager
import retrofit2.Response

class WaterLogRepo(
    val sessionManager: SessionManager
){


    val tokens=sessionManager.getAccessToken()

    val api= ApiClient.waterLogApi

    suspend fun waterPlant(request: WaterLogReq) : Response<WaterLogResponse>{

        return api.waterPlant(
            "Bearer $tokens",
            request
        )
    }

    suspend fun get_water_streak(id: Int) : Response<WaterStreakResponse>{

        return api.getDailyWaterLog(
            "Bearer $tokens",
            id
        )

    }

}