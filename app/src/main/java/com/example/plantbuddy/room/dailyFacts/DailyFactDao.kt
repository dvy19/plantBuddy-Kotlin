package com.example.plantbuddy.room.dailyFacts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DailyFactDao {

    @Query("SELECT * FROM daily_facts_table WHERE date = :date LIMIT 1")
    suspend fun getFactForDate(date: String): DailyFactEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFact(fact: DailyFactEntity)

    @Query("DELETE FROM daily_facts_table")
    suspend fun clearFacts()
}