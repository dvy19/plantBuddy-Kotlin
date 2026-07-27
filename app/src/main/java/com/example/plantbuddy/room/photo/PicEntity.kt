package com.example.plantbuddy.room.photo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="pic_table")
data class OfflinePicEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    var imagePath:String,

    var note:String,

    var plant_name:String,

    val savedAt: Long = System.currentTimeMillis()

)