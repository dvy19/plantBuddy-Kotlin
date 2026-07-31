package com.example.plantbuddy.waterStreak

data class WaterLogReq (
    var plant:Int,

)
data class WaterLogResponse(
    val id: Int,
    val watered_on: String,
    val created_at: String,
    val user: Int,
    val plant: Int
)
data class WaterStreakResponse(
    val streak: Int,
    val watered_today: Boolean,
    val watered_dates: List<String>,
    val logs: List<WaterLogResponse>
)