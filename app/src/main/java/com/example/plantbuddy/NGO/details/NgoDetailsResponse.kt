package com.example.plantbuddy.NGO.details

data class NgoDetailsResponse (

    var name:String,
    var description:String,

    var city:String,
    var address:String,
    var phone_number: String,

    var website:String,

    var logo:String?
)