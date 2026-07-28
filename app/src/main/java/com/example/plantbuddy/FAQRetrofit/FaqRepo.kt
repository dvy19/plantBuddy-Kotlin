package com.example.plantbuddy.FAQRetrofit

import com.example.plantbuddy.ApiClient
import retrofit2.Response

class FaqRepo(){

    val apiService= ApiClient.getFaq

    suspend fun getPlantFaq(request: FaqRequest) : Response<FaqResponse>{

        return apiService.getFaq(request)


    }

}