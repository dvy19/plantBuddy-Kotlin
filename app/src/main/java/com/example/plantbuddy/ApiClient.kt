package com.example.plantbuddy

import com.example.plantbuddy.plants.PlantInterface
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


}