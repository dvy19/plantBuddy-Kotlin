package com.example.plantbuddy.room.PersonalPlant

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="personal_plant")
data class PersonalPlantEntity (
    @PrimaryKey
    var plant_id:Int,

    var plant_name:String,

    var plant_type:String,

    var water_requirement:String,

    var image_url:String
)