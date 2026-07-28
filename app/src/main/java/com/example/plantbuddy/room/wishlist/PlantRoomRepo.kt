package com.example.plantbuddy.room.wishlist

import kotlinx.coroutines.flow.Flow

class PlantRoomRepo(
    private val dao: PlantDao
) {

    suspend fun insertPlant(plant:PlantEntity){
        dao.insertPlant(plant)
    }

     suspend fun getAllPlants()=dao.getAllPlants()

}