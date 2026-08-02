package com.example.plantbuddy.NGO.campaign

data class AllCampaignResponse (
    val message: String,
    val data: List<Campaign>
)

data class SingleCampaignResponse(
    val message: String,
    val data: Campaign
)

data class Campaign(
    val id: Int,
    val title: String,
    val description: String,
    val location: String,
    val current_volunteers: Int,
    val required_volunteers: Int,
    val logo: String?,
    val goal_amount: String,
    val current_amount: String,
    val start_date: String,
    val end_date: String,
    val is_active: Boolean,
    val created_at: String,
    val updated_at: String,
    val ngo: Int
)

/*
{
    "message": "Campaigns retrieved successfully",
    "data": [
        {
            "id": 1,
            "title": "jgvv",
            "description": "jjbv",
            "location": "hhb",
            "current_volunteers": 0,
            "required_volunteers": 65,
            "logo": null,
            "goal_amount": "65.00",
            "current_amount": "0.00",
            "start_date": "2005-12-12",
            "end_date": "2005-12-12",
            "is_active": true,
            "created_at": "2026-08-02T04:19:20.714591Z",
            "updated_at": "2026-08-02T04:19:20.714603Z",
            "ngo": 11
        }
    ]
}
 */