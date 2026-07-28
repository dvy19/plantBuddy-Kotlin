package com.example.plantbuddy.room.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


sealed class SavedPlantState{
    object Idle: SavedPlantState()
    object Loading: SavedPlantState()
    object Success: SavedPlantState()
    data class Error(val message: String): SavedPlantState()
}
class PlantRoomViewModel(
    private val repo: PlantRoomRepo
) : ViewModel(){

    private val _savePlantState = MutableStateFlow<SavedPlantState>(SavedPlantState.Idle)
    val savePlantState: StateFlow<SavedPlantState> = _savePlantState.asStateFlow()


    suspend fun getAllSavedPlants()=repo.getAllPlants()

    fun savePlantOffline(plant: PlantEntity){

        viewModelScope.launch {

            _savePlantState.value = SavedPlantState.Loading


            try{

                val response=repo.insertPlant(plant)

                _savePlantState.value = SavedPlantState.Success

            }catch (e:Exception){

                _savePlantState.value = SavedPlantState.Error(e.localizedMessage ?: "Failed to save plant")

            }
        }

        }




}