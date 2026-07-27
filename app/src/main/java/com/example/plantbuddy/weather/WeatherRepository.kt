package com.example.plantbuddy.weather

import com.example.plantbuddy.ApiClient
import com.example.plantbuddy.BuildConfig
import retrofit2.Response

class WeatherRepository {

    val api = ApiClient.weatherApiService


    suspend fun getWeather(city:String)  : Response<WeatherResponse>{

        return ApiClient.weatherApiService.getWeather(
            city = city,
            apiKey ="8267882bacb857c78cbec307780a8309"
        )

    }
}