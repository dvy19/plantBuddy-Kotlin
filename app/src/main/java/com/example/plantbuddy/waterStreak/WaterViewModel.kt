package com.example.plantbuddy.waterStreak

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class WaterPlantState{
    object Idle : WaterPlantState()
    object Loading : WaterPlantState()
    data class Success(val data: WaterLogResponse) : WaterPlantState()
    data class Error(val message: String) : WaterPlantState()
}

sealed class GetWaterStreakState{
    object Idle : GetWaterStreakState()
    object Loading : GetWaterStreakState()
    data class Success(val data: WaterStreakResponse) : GetWaterStreakState()
    data class Error(val message: String) : GetWaterStreakState()

}
class WaterViewModel(
    val repo: WaterLogRepo
) : ViewModel(){

    private val _waterPlantState = MutableStateFlow<WaterPlantState>(WaterPlantState.Idle)
    val waterPlantState: StateFlow<WaterPlantState> = _waterPlantState.asStateFlow()

    private val _getWaterStreakState = MutableStateFlow<GetWaterStreakState>(GetWaterStreakState.Idle)
    val getWaterStreakState: StateFlow<GetWaterStreakState> = _getWaterStreakState.asStateFlow()

    fun getWaterStreak(id: Int){

        _getWaterStreakState.value = GetWaterStreakState.Loading

        viewModelScope.launch{

            try {
                val response = repo.get_water_streak(id)

                if(response.isSuccessful && response.body()!=null){
                    _getWaterStreakState.value = GetWaterStreakState.Success(response.body()!!)
                }
                else{
                    throw Exception("Failed to get water streak")
                    }
                } catch (e: Exception) {
                    _getWaterStreakState.value = GetWaterStreakState.Error(e.message ?: "An error occurred")
                }
        }
    }

    fun waterPlant(request: WaterLogReq){

        _waterPlantState.value = WaterPlantState.Loading

        viewModelScope.launch{

            try {
                val response = repo.waterPlant(request)


                if(response.isSuccessful && response.body()!=null){
                    _waterPlantState.value = WaterPlantState.Success(response.body()!!)
                }
                else{
                    throw Exception("Failed to water plant")
                    }
                } catch (e: Exception) {
                    _waterPlantState.value = WaterPlantState.Error(e.message ?: "An error occurred")
                }
            }
        }




}