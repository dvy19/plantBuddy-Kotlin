package com.example.plantbuddy.room.dailyFacts

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="daily_facts_table")
data class DailyFactEntity (

    @PrimaryKey
    var date: String,

    var title:String,
    var fact:String,
    var category:String

)