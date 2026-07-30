package com.example.plantbuddy.userDetails

import com.example.plantbuddy.ApiClient
import com.example.plantbuddy.auth.SessionManager
import retrofit2.Response

class UserDetailRepo(
    val sessionManager: SessionManager
) {

    val token=sessionManager.getAccessToken()


    val api= ApiClient.detailApi

    suspend fun create_user_profile(request: UserDetailReq) : Response<UserDetailResponse>{

        return api.createProfile(
            "Bearer $token",
            request
        )
    }

    suspend fun get_user_profile() : Response<UserDetailResponse>{

        return api.getProfile(
            "Bearer $token"
        )
    }



}