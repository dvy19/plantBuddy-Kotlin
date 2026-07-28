package com.example.plantbuddy.FAQRetrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FaqApiInterface {

    @POST("api/plants/plant-faq/")
    suspend fun getFaq(
        @Body request: FaqRequest
    ) : Response<FaqResponse>


    }
