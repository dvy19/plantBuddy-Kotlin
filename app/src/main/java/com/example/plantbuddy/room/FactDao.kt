package com.example.plantbuddy.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertFact(fact: SavedFact)

        @Delete
        suspend fun deleteFact(fact: SavedFact)

        @Query("SELECT * FROM saved_facts ORDER BY savedAt DESC")
        fun getAllFacts(): Flow<List<SavedFact>>

        @Query("SELECT EXISTS(SELECT 1 FROM saved_facts WHERE title = :title AND fact = :content)")
        suspend fun isSaved(title: String, content: String): Boolean

}