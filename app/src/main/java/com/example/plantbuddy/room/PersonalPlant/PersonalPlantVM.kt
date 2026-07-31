package com.example.plantbuddy.room.PersonalPlant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class SavePersonalPlantState{
    object Idle: SavePersonalPlantState()
    object Loading: SavePersonalPlantState()
    data class Success(val data: PersonalPlantEntity): SavePersonalPlantState()
    data class Error(val message:String): SavePersonalPlantState()
}

sealed class GetAllPersonalPlant{
    object Idle: GetAllPersonalPlant()
    object Loading: GetAllPersonalPlant()
    data class Success(val data: List<PersonalPlantEntity>): GetAllPersonalPlant()
    data class Error(val message:String): GetAllPersonalPlant()
}
class PersonalPlantVM(
    private val repo: PersonalPlantRepo
) : ViewModel() {

    private val _savePersonalPlantState = MutableStateFlow<SavePersonalPlantState>(
        SavePersonalPlantState.Idle
    )

    val savePersonalPlantState: StateFlow<SavePersonalPlantState> = _savePersonalPlantState.asStateFlow()


    fun insertPersonalPlant(personalPlant: PersonalPlantEntity){



        viewModelScope.launch {

            _savePersonalPlantState.value = SavePersonalPlantState.Loading

            try{
                repo.insertPersonalPlant(personalPlant)
                _savePersonalPlantState.value = SavePersonalPlantState.Success(personalPlant)
            }
            catch (e:Exception){
                _savePersonalPlantState.value = SavePersonalPlantState.Error(e.message.toString())
            }
        }
    }


    var getPersonalPlants  = repo.getAllPersonalPlant()




}
