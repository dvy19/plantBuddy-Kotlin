package com.example.plantbuddy.room.PersonalPlant

import kotlinx.coroutines.flow.Flow

class PersonalPlantRepo(
    private val dao: PersonalPlantDao
) {


    suspend fun insertPersonalPlant(personalPlant: PersonalPlantEntity){
        dao.insertPersonalPlant(personalPlant)
    }

    suspend fun deletePersonalPlant(personalPlant: PersonalPlantEntity){
        dao.deletePersonalPlant(personalPlant)
    }

    fun getAllPersonalPlant() =dao.getAllPersonalPlant()



}