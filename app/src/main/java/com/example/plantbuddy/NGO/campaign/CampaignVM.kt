package com.example.plantbuddy.NGO.campaign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody


sealed class CreateCampaignState{
    object Idle : CreateCampaignState()
    object Loading : CreateCampaignState()
    data class Success(val data: CampaignResponse) : CreateCampaignState()
    data class Error(val message: String) : CreateCampaignState()
}

sealed class GetCampaignState{
    object Idle : GetCampaignState()
    object Loading : GetCampaignState()
    data class Success(val data: CampaignResponse) : GetCampaignState()
    data class Error(val message: String) : GetCampaignState()
}

class CampaignVM(
    private val repo: CampaignRepo
) : ViewModel(){

    private val _state = MutableStateFlow<CreateCampaignState>(CreateCampaignState.Idle)
    val state: StateFlow<CreateCampaignState> = _state.asStateFlow()

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

        }

