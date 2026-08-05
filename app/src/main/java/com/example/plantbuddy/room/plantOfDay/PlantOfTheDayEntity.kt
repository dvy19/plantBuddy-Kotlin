package com.example.plantbuddy.room.plantOfDay

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PlantOfTheDayEntity (

    @PrimaryKey
    var id:Int

)