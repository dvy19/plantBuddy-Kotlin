package com.example.plantbuddy.NGO.campaign

data class CampaignResponse (
    var title: String,

    var description: String,

    var location: String,
    var start_date:String,
    var end_date:String,

    var goal_amount: Double,
    var required_volunteers:String,

    var is_active:String,
    var logo: String?
)