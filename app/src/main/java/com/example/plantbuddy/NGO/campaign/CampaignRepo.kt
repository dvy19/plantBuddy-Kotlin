package com.example.plantbuddy.NGO.campaign

import com.example.plantbuddy.ApiClient
import com.example.plantbuddy.auth.SessionManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class CampaignRepo (
    private val sessionManager: SessionManager
){

    val api=ApiClient.campaignApi

    fun String.toPlainText(): RequestBody {
        return this.toRequestBody("text/plain".toMediaType())
    }


    val token=sessionManager.getAccessToken()


    suspend fun create_campaign(
        title: String,
        description: String,
        location: String,
        start_date: String,
        end_date: String,
        goal_amount: Double,
        required_volunteers: Int,
        is_active: Boolean,
        logo: MultipartBody.Part?
    ) : Response<CampaignResponse>{

        return api.createCampaign(

            "Bearer $token",
            title = title.toPlainText(),
            description = description.toPlainText(),
            location = location.toPlainText(),

            start_date = start_date.toPlainText(),
            end_date = end_date.toPlainText(),
            goal_amount = goal_amount.toString().toPlainText(),
            required_volunteers = required_volunteers.toString().toPlainText(),
            is_active = is_active.toString().toPlainText(),
            logo = logo
        )

        }

    }
