package com.example.plantbuddy.plants

import com.example.plantbuddy.ApiClient
import com.example.plantbuddy.room.dailyFacts.DailyFactDao
import com.example.plantbuddy.room.dailyFacts.DailyFactEntity
import retrofit2.Response
import java.time.LocalDate

class PlantRepo(
    private val dao: DailyFactDao
) {

    private val plantInterface = ApiClient.getAllPlantsApi


    suspend fun get_all_plants(page:Int) : Response<PlantPageResponse> {

        return plantInterface.getAllPlants(page)
    }

    suspend fun getPlantFact(): Fact {

        val today = LocalDate.now().toString()

        // Check Room
        val localFact = dao.getFactForDate(today)

        if (localFact != null) {

            return Fact(
                title = localFact.title,
                fact = localFact.fact,
                category = localFact.category
            )
        }

        // Fetch from API
        val response = plantInterface.getFact()

        if (response.isSuccessful && response.body() != null) {

            val apiFact = response.body()!!

            dao.insertFact(
                DailyFactEntity(
                    date = today,
                    title = apiFact.title,
                    fact = apiFact.fact,
                    category = apiFact.category
                )
            )

            return apiFact
        }

        throw Exception("Unable to fetch fact.")
    }

    suspend fun get_single_plant(id:Int): Response<SinglePlantResponse>{

        return plantInterface.getSinglePlant(id)
    }




}