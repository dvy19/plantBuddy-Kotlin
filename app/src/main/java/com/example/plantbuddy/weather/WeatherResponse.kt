package com.example.plantbuddy.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind,
    val rain: Rain?,
    val sys: Sys,
    val name:String
)

data class Main(
    val temp:Double,
    val humidity:Int
)

data class Weather(
    val main:String,
    val description:String
)

data class Wind(
    val speed:Double
)

data class Rain(
    @SerializedName("1h")
    val oneHour:Double?
)

data class Sys(
    val sunrise:Long,
    val sunset:Long
)