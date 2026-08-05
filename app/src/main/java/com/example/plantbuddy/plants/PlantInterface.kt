package com.example.plantbuddy.plants

import com.example.plantbuddy.room.plantOfDay.PlantDayResponse
import com.example.plantbuddy.room.plantOfDay.PlantOFDayReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PlantInterface{

    @GET("api/plants/allPlants/")
    suspend fun getAllPlants(
        @Query("page") page: Int,
        @Query("search") search: String? = null
    ) : Response<PlantPageResponse>

    @GET("api/plants/getFactOfDay/")
    suspend fun getFact(): Response<Fact>


    @GET("api/plants/singlePlants/{id}/")
    suspend fun getSinglePlant(
        @Path("id") id: Int
    ) : Response<SinglePlantResponse>


    @POST("api/plants/plant-of-the-day/")
    suspend fun getPlantOfDay(
        @Body request: PlantOFDayReq
    ) : Response<PlantDayResponse>


}