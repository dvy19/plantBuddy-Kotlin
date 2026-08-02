package com.example.plantbuddy.NGO.details

data class NgoDetailsResponse(

    val message: String,
    val data: NgoDetailsData
)

data class NgoDetailsData(
    val id: Int,
    val name: String,
    val description: String,
    val address: String,
    val phone_number: String,
    val website: String,
    val city: String,
    val logo: Any?,
    val created_at: String,
    val updated_at: String,
    val user: Int
)


/*
{
    {
    "message": "NGO retrieved successfully",
    "data": {
        "id": 11,
        "name": "hvv",
        "description": "hgvb",
        "address": "jgvv",
        "phone_number": "58658535",
        "website": "https://ramraksha.com",
        "city": "jgcv",
        "logo": null,
        "created_at": "2026-08-02T03:59:54.658409Z",
        "updated_at": "2026-08-02T03:59:54.658425Z",
        "user": 26
    }
}
}
 */