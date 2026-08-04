package com.example.plantbuddy.plants

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantbuddy.room.DatabaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class GetAllPlantsState {
    object Idle: GetAllPlantsState()
    object Loading: GetAllPlantsState()
    data class Success(
        val data: List<Plant>,
        val count: Int,
        val next: String?,
        val previous: String?

    ): GetAllPlantsState()

    data class Error(val message:String): GetAllPlantsState()
}

sealed class GetFactState{
    object Idle: GetFactState()
    object Loading: GetFactState()
    data class Success(val data: Fact): GetFactState()
    data class Error(val message:String): GetFactState()
}

sealed class GetSinglePlantState{
    object Idle: GetSinglePlantState()
    object Loading: GetSinglePlantState()
    data class Success(val data: Plant): GetSinglePlantState()
    data class Error(val message:String): GetSinglePlantState()
}
class PlantViewModel(
    private val repo :PlantRepo


) : ViewModel() {

    private val _getPlantsState = MutableStateFlow<GetAllPlantsState>(
        GetAllPlantsState.Idle
    )

    val getPlantsState: StateFlow<GetAllPlantsState> =
        _getPlantsState.asStateFlow()

    private val _getFactState = MutableStateFlow<GetFactState>(
        GetFactState.Idle
    )

    val getFactState: StateFlow<GetFactState> = _getFactState.asStateFlow()

    private val _getSinglePlantState = MutableStateFlow<GetSinglePlantState>(
        GetSinglePlantState.Idle
    )
    val getSinglePlantState: StateFlow<GetSinglePlantState> = _getSinglePlantState.asStateFlow()


    private var currentPage = 1

    private var hasNextPage = true

    private val allPlants = mutableListOf<Plant>()

    var currentSearch = ""
    private var isLoadingMore = false

    fun getAllPlants(page: Int = 1, search:String = currentSearch) {

        viewModelScope.launch {

            if (page == 1) {
                _getPlantsState.value = GetAllPlantsState.Loading
                allPlants.clear()
            }


            try {

                val response = repo.get_all_plants(page  , search)

                if (response.isSuccessful && response.body() != null) {

                    val body = response.body()!!

                    currentPage = page

                    hasNextPage = body.next != null

                    // Append instead of replace
                    allPlants.addAll(body.results.data)

                    _getPlantsState.value = GetAllPlantsState.Success(
                        data = allPlants.toList(),
                        count = body.count,
                        next = body.next,
                        previous = body.previous
                    )

                    isLoadingMore = false
                } else {
                    Log.d("m", getPlantsState.value.toString())


                    _getPlantsState.value =
                        GetAllPlantsState.Error(

                            response.message()
                                ?: "Failed to fetch posts"
                        )
                }


            } catch (e: Exception) {
                _getPlantsState.value = GetAllPlantsState.Error(e.message.toString())


            }

        }
    }

    fun loadNextPage() {

        if (!hasNextPage || isLoadingMore) return

        isLoadingMore = true


        getAllPlants(currentPage + 1)

    }

    fun getFact() {

        viewModelScope.launch {

            _getFactState.value = GetFactState.Loading

            try {
                val response = repo.getPlantFact()
                _getFactState.value = GetFactState.Success(response)
            } catch (e: Exception) {
                _getFactState.value = GetFactState.Error(e.message.toString())
            }
        }



    }

    fun getSinglePlant(id:Int){

        viewModelScope.launch {

            _getSinglePlantState.value = GetSinglePlantState.Loading

            try{
                val response = repo.get_single_plant(id)
                Log.d("m", response.body().toString())

                //Log.d("m", response.message())
                Log.d("m", response.code().toString())
                Log.d("m", response.isSuccessful.toString())
                Log.d("m", response.errorBody().toString())
                //Log.d("m", response.raw().toString())

                if(response.body() != null && response.isSuccessful){
                    _getSinglePlantState.value = GetSinglePlantState.Success(response.body()!!.data)
                }
                else{
                    _getSinglePlantState.value = GetSinglePlantState.Error( response.message() ?: "Failed to fetch posts")
                }


        }
            catch (e:Exception){
                _getSinglePlantState.value = GetSinglePlantState.Error(e.message.toString())
            }
        }


    }


}