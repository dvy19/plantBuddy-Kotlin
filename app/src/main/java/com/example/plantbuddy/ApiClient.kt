package com.example.plantbuddy

import com.example.plantbuddy.FAQRetrofit.FaqApiInterface
import com.example.plantbuddy.NGO.campaign.CampaignInterface
import com.example.plantbuddy.NGO.details.NgoApiInterface
import com.example.plantbuddy.auth.AuthInterface
import com.example.plantbuddy.plants.PlantInterface
import com.example.plantbuddy.userDetails.DetailsInterface
import com.example.plantbuddy.waterStreak.WaterInterface
import com.example.plantbuddy.weather.WeatherApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {

    private const val BASE_URL = "https://plantbuddybackend.onrender.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private const val WEATHER_BASE_URL="https://api.openweathermap.org/"

    val weatherApiService:WeatherApi by lazy {

        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }


    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val getAllPlantsApi: PlantInterface by lazy {
        retrofit.create(PlantInterface::class.java)
    }

    val getFactApi: PlantInterface by lazy{
        retrofit.create(PlantInterface::class.java)
    }

    val getFaq: FaqApiInterface by lazy{
        retrofit.create(FaqApiInterface::class.java)
    }


    val registerApi: AuthInterface by lazy{
        retrofit.create(AuthInterface::class.java)
    }

    val loginApi: AuthInterface by lazy{
        retrofit.create(AuthInterface::class.java)
    }

    val detailApi: DetailsInterface by lazy{
        retrofit.create(DetailsInterface::class.java)
    }

    val waterLogApi: WaterInterface by lazy{
        retrofit.create(WaterInterface::class.java)
    }

    val ngoDetailsApi: NgoApiInterface by lazy{
        retrofit.create(NgoApiInterface::class.java)
    }

    val campaignApi:CampaignInterface by lazy{
        retrofit.create(CampaignInterface::class.java)
    }

    }


