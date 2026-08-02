package com.example.plantbuddy.NGO.details

import com.example.plantbuddy.ApiClient
import com.example.plantbuddy.auth.SessionManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class NgoDetailsRepo(
    private val sessionManager: SessionManager
) {

    val api= ApiClient.ngoDetailsApi

    fun String.toPlainText(): RequestBody {
        return this.toRequestBody("text/plain".toMediaType())
    }


    val token=sessionManager.getAccessToken()



    suspend fun createNgo(

            name: String,
            description: String,
            phone: String,
            city: String,
            address: String,
            website: String,
            logo: MultipartBody.Part?

        ): Response<NgoDetailsResponse> {

            return api.createNGO(

                "Bearer $token",

                name = name.toPlainText(),

                description = description.toPlainText(),

                phone = phone.toPlainText(),

                city = city.toPlainText(),

                address = address.toPlainText(),

                website = website.toPlainText(),

                logo = logo

            )

        }

    suspend fun get_ngo_profile() : Response<NgoDetailsResponse> {

        return api.getNgoProfile("Bearer $token")
    }


}


