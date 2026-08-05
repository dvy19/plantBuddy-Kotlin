package com.example.plantbuddy.userDetails

data class VolunteerProfileReq (

    var name:String,
    var phone:String,
    var city: String,
    var gender:String,
)

data class VolunteerProfileResponse (
    val message:String,
    val data:VolunteerProfile
)

data class VolunteerProfile(
    val name:String,
    val phone:String,
    val city: String,
    val gender:String,
    val email:String,
    var id:Int,
    val image:String?
)