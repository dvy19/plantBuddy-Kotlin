package com.example.plantbuddy.NGO.campaign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response


sealed class CreateCampaignState{
    object Idle : CreateCampaignState()
    object Loading : CreateCampaignState()
    data class Success(val data: SingleCampaignResponse) : CreateCampaignState()
    data class Error(val message: String) : CreateCampaignState()
}

sealed class GetCampaignState{
    object Idle : GetCampaignState()
    object Loading : GetCampaignState()
    data class Success(val data: SingleCampaignResponse) : GetCampaignState()
    data class Error(val message: String) : GetCampaignState()
}


sealed class GetActiveCampaignState{
    object Idle : GetActiveCampaignState()
    object Loading : GetActiveCampaignState()
    data class Success(val data: List<Campaign>) : GetActiveCampaignState()
    data class Error(val message: String) : GetActiveCampaignState()
}

class CampaignVM(
    private val repo: CampaignRepo
) : ViewModel(){

    private val _state = MutableStateFlow<CreateCampaignState>(CreateCampaignState.Idle)
    val state: StateFlow<CreateCampaignState> = _state.asStateFlow()

    private val _getActiveCampaignState = MutableStateFlow<GetActiveCampaignState>(GetActiveCampaignState.Idle)
    val getActiveCampaignState: StateFlow<GetActiveCampaignState> = _getActiveCampaignState.asStateFlow()

    fun createNgoCampaign(

        title: String,
        description: String,
        location: String,
        start_date: String,
        end_date: String,
        goal_amount: Double,
        required_volunteers: Int,
        is_active: Boolean,
        logo: MultipartBody.Part?
    ){

        viewModelScope.launch {
            _state.value = CreateCampaignState.Loading

            try{
                val response = repo.create_campaign(
                    title,
                    description,
                    location,
                    start_date,
                    end_date,
                    goal_amount,
                    required_volunteers,
                    is_active,
                    logo
                    )

                if(response.isSuccessful){
                    _state.value = CreateCampaignState.Success(response.body()!!)
                }else{
                    _state.value = CreateCampaignState.Error(response.message())
                }
            }catch (e: Exception){
                _state.value = CreateCampaignState.Error(e.message ?: "An error occurred")
            }

                }
            }

    private val _getCampaignState = MutableStateFlow<GetCampaignState>(GetCampaignState.Idle)
    val getCampaignState: StateFlow<GetCampaignState> = _getCampaignState.asStateFlow()

    /*
    fun getCampaign(){

        viewModelScope.launch {
            _getCampaignState.value = GetCampaignState.Loading

            try{
                val response = repo.getCampaign()

                if(response.isSuccessful){
                    _getCampaignState.value = GetCampaignState.Success(response.body()!!)
                }else{
                    _getCampaignState.value = GetCampaignState.Error(response.message())
                    }
            }catch (e: Exception){
                _getCampaignState.value = GetCampaignState.Error(e.message ?: "An error occurred")
            }
        }


     */

    fun getActiveCampaign(is_active: Boolean) {

        viewModelScope.launch {
            _getActiveCampaignState.value = GetActiveCampaignState.Loading

            try{
                val response = repo.get_active_campaigns(is_active)

                if(response.isSuccessful){
                    _getActiveCampaignState.value = GetActiveCampaignState.Success(response.body()!!.data)
                }else{
                    _getActiveCampaignState.value = GetActiveCampaignState.Error(response.message())
                }
                }catch (e: Exception){
                _getActiveCampaignState.value = GetActiveCampaignState.Error(e.message ?: "An error occurred")
            }


        }

    }



}



