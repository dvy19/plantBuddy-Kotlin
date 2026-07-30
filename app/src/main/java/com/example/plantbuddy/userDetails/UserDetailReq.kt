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
    var id:Int,
    var name:String,
    var city:String,
    var user:Int
)

/*
{
    "message": "Profile saved successfully",
    "data": {
        "id": 1,
        "name": "revamp",
        "city": "kanpur",
        "user": 8
    }
}
 */