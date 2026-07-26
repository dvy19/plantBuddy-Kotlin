package com.example.plantbuddy.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "facts")
data class Fact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val fact: String,
    val category: String

)