package com.example.plantbuddy.weather

import com.example.plantbuddy.ApiClient
import com.example.plantbuddy.BuildConfig
import retrofit2.Response

class WeatherRepository {

    val api = ApiClient.weatherApiService


    suspend fun getWeather(city:String)  : Response<WeatherResponse>{

        return ApiClient.weatherApiService.getWeather(
            city = city,
            apiKey = BuildConfig.WEATHER_API_KEY
        )

    }


}