package com.example.plantbuddy.userDetails

import com.example.plantbuddy.ApiClient
import com.example.plantbuddy.auth.SessionManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class UserDetailRepo(
    val sessionManager: SessionManager
) {

    val token=sessionManager.getAccessToken()



    fun String.toPlainText(): RequestBody {
        return this.toRequestBody("text/plain".toMediaType())
    }


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

    suspend fun get_volunteer_profile() : Response<VolunteerProfileResponse>{
        return api.getVolunteerProfile(
            "Bearer $token"
        )
    }

    suspend fun create_volunteer_profile(
        name:String,
        phone:String,
        city:String,
        gender:String,
        image:MultipartBody.Part?
    ) : Response<VolunteerProfileResponse>{
        return api.createVolunteerProfile(
            "Bearer $token",

            name = name.toPlainText(),
            phone = phone.toPlainText(),
            city = city.toPlainText(),
            gender = gender.toPlainText(),
            image = image

        )

    }


}