package com.example.plantbuddy.plants

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

data class PlantsResponse(
    val message: String,
    val data: List<Plant>
)

data class Plant(
    val id: Int,
    val category: PlantAttribute,
    val plant_type: PlantAttribute,
    val light_requirement: PlantAttribute,
    val water_requirement: PlantAttribute,
    val growth_rate: PlantAttribute,
    val lifespan: PlantAttribute,
    val soil_type: PlantAttribute,
    val best_planting_season: PlantAttribute,
    val flowering_season: PlantAttribute?,
    val fruiting_season: PlantAttribute?,
    val name: String,
    val scientific_name: String,
    val description: String,
    val temperature_min: Int,
    val temperature_max: Int,
    val humidity: String,
    val average_height: String,
    val fertilizer: String,
    val repotting_frequency: String,
    val pruning_required: Boolean,
    val pet_friendly: Boolean,
    val air_purifying: Boolean,
    val edible: Boolean,
    val image_url: String,
    val created_at: String,
    val updated_at: String
)

data class SinglePlantResponse(
    var message:String,
    var data:Plant
)

data class PlantAttribute(
    val id: Int,
    val name: String
)


@Parcelize
data class Fact(
    var title:String,
    var fact:String,
    var category:String
) : Parcelable