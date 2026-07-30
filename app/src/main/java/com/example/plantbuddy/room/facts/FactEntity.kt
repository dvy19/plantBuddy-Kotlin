package com.example.plantbuddy.room.facts

import androidx.room.Entity


@Entity(tableName="facts_table")
data class FactEntity (

    @PrimaryKey(autoGenerate = true)
    var id:Int,


)