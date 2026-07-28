package com.example.plantbuddy.room.wishlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="saved_plants")
data class PlantEntity(

    @PrimaryKey(autoGenerate = true)
    var id:Int,

    var name:String,
    var description :String,

    var image_url:String?,

    var homePlace:String,

    var type:String

)