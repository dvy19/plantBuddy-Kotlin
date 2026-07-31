package com.example.plantbuddy.room.PersonalPlant

import androidx.room.Dao
import com.example.plantbuddy.room.SavedFact

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonalPlantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonalPlant(fact: PersonalPlantEntity)

    @Delete
    suspend fun deletePersonalPlant(fact: PersonalPlantEntity)

    @Query("SELECT * FROM personal_plant")
    fun getAllPersonalPlant(): Flow<List<PersonalPlantEntity>>

    /*

    @Query("SELECT EXISTS(SELECT 1 FROM saved_facts WHERE title = :title AND fact = :content)")
    suspend fun isSaved(title: String, content: String): Boolean

     */

}