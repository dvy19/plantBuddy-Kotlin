package com.example.plantbuddy.plants

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlantInterface{

    @GET("api/plants/allPlants/")
    suspend fun getAllPlants() : Response<PlantsResponse>

    @GET("api/plants/getFactOfDay/")
    suspend fun getFact(): Response<Fact>


    @GET("api/plants/singlePlants/{id}/")
    suspend fun getSinglePlant(
        @Path("id") id: Int
    ) : Response<SinglePlantResponse>


}