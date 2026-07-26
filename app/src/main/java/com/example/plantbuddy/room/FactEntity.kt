package com.example.plantbuddy.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "saved_facts")
data class SavedFact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val fact: String,
    val category: String,
    val savedAt: Long = System.currentTimeMillis()


)