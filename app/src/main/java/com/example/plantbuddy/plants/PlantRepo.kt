package com.example.plantbuddy.plants

import com.example.plantbuddy.ApiClient
import retrofit2.Response

class PlantRepo() {

    private val plantInterface = ApiClient.getAllPlantsApi


    suspend fun get_all_plants() : Response<PlantsResponse> {

        return plantInterface.getAllPlants()
    }

    suspend fun get_plant_fact(): Response<Fact>{

        return plantInterface.getFact()
    }

    suspend fun get_single_plant(id:Int): Response<SinglePlantResponse>{

        return plantInterface.getSinglePlant(id)
    }




}