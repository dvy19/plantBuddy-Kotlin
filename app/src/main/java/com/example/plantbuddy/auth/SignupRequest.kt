package com.example.plantbuddy.auth

data class SignupRequest (

    var email:String,
    var password: String,
    var role:String
)

data class SignupResponse(
    var message: String,
    var tokens:Token,
    var role:String,
)

data class Token(
    var refresh: String,
    var access:String
)

/*
{
    "message": "Registration successful",
    "role": "user",
    "tokens": {
        "refresh": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoicmVmcmVzaCIsImV4cCI6MTc4NTQyNzAyNiwiaWF0IjoxNzg1MzQwNjI2LCJqdGkiOiJjNDYxZDk0YTE3YzA0N2Q4ODBjODUyMDAxNDRmNDM1ZSIsInVzZXJfaWQiOiIxIn0.k81JYDJ3RSeKKYj0e_gJBamxP7umjI8JlMFjRrbJ_cg",
        "access": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzg1MzQwOTI2LCJpYXQiOjE3ODUzNDA2MjYsImp0aSI6IjVmOWQ5MWExMDhiYzQzNzI5Nzc2MzY1MGQ1NGUyZDJjIiwidXNlcl9pZCI6IjEifQ.NCtqzJcA-Vt7iPIUOyo7r3eMkttpXiAL13OjSy7BI1g"
    }
}
 */

data class LoginRequest(
    var email:String,
    var password:String
)

data class LoginResponse(
    var message: String,
    var tokens:Token,
    var role:String
)


data class DeviceTokenRequest(
    var token:String
)