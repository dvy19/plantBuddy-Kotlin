package com.example.plantbuddy.userDetails

data class UserDetailReq (

    var name:String,
    var city:String
)

data class UserDetailResponse(

    var message:String,
    var data:Userdata
)

data class Userdata(
    var name:String,
    var city:String
)