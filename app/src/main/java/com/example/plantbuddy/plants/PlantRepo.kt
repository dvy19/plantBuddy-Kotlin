package com.example.plantbuddy.plants

import com.example.plantbuddy.ApiClient
import retrofit2.Response

class PlantRepo() {

    private val plantInterface = ApiClient.getAllPlantsApi


    suspend fun get_all_plants(page:Int) : Response<PlantPageResponse> {

        return plantInterface.getAllPlants(page)
    }

    suspend fun get_plant_fact(): Response<Fact>{

        return plantInterface.getFact()
    }

    suspend fun get_single_plant(id:Int): Response<SinglePlantResponse>{

        return plantInterface.getSinglePlant(id)
    }




}