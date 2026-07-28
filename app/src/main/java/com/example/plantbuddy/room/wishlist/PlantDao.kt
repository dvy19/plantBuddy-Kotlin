package com.example.plantbuddy.room.wishlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao{

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant:PlantEntity)

    @Query("SELECT * FROM saved_plants")
    suspend fun getAllPlants():Flow<List<PlantEntity>>


}