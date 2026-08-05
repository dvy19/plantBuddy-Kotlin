package com.example.plantbuddy.room.plantOfDay


data class PlantOFDayReq(
    val city:String,
    val temperature:Double,
    val humidity:Int,
    val weather:String,
    val description:String,
    val wind_speed:Double,
    val rain:Double,
    val sunrise:Long,
    val sunset:Long

)

/*
{
    "city": "Kanpur",
    "temperature": 32.8,
    "humidity": 68,
    "weather": "Clouds",
    "description": "broken clouds",
    "wind_speed": 4.5,
    "rain": 0.0,
    "sunrise": 1754287200,
    "sunset": 1754337400
}
 */

data class PlantDayResponse (

    val message:String,
    val data:PlantOfTheDay

)

data class PlantOfTheDay(
    val name:String,

    val scientific_name:String,
    val category:String,
    val why_today:String,
    val care_tip:String,
    val fun_fact:String,
    val watering:String,
    val sunlight:String,
    val difficulty:String,
    val pet_friendly:Boolean,
    val air_purifying:Boolean

)

/*
{
    "message": "Plant of the day generated successfully.",
    "data": {
        "name": "Hibiscus",
        "scientific_name": "Hibiscus rosa-sinensis",
        "category": "Flowering",
        "why_today": "Kanpur's warm temperature of 32.8°C and moderate humidity of 68% create the ideal tropical-like environment for the Hibiscus to thrive and produce vibrant blooms under broken clouds.",
        "care_tip": "With moderate humidity and no recent rainfall, ensure the soil stays consistently moist but not waterlogged.",
        "fun_fact": "Hibiscus flowers are edible and are commonly used to make herbal teas rich in vitamin C.",
        "watering": "Moderate",
        "sunlight": "Full Sun",
        "difficulty": "Easy",
        "pet_friendly": true,
        "air_purifying": false
    }
}
 */