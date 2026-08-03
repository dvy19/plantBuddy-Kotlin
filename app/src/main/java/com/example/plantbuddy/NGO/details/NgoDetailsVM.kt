package com.example.plantbuddy.NGO.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody


sealed class NgoCreateState{
    object Idle: NgoCreateState()
    object Loading: NgoCreateState()
    data class Success(val data: NgoDetailsResponse): NgoCreateState()
    data class Error(val message: String): NgoCreateState()
}

sealed class GetNgoProfileState{
    object Idle: GetNgoProfileState()
    object Loading: GetNgoProfileState()
    data class Success(val data: NgoDetailsResponse): GetNgoProfileState()
    data class Error(val message: String): GetNgoProfileState()
}

sealed class GetAllNgoState{
    object Idle: GetAllNgoState()
    object Loading: GetAllNgoState()
    data class Success(val data: List<NgoDetailsData>): GetAllNgoState()
    data class Error(val message: String): GetAllNgoState()
}

sealed class GetSingleNgoState{
    object Idle: GetSingleNgoState()
    object Loading: GetSingleNgoState()
    data class Success(val data: NgoDetailsData): GetSingleNgoState()
    data class Error(val message: String): GetSingleNgoState()
}

class NgoDetailsVM(
    private val repo: NgoDetailsRepo
) : ViewModel() {

    val _ngoCreateState = MutableStateFlow<NgoCreateState>(NgoCreateState.Idle)
    val ngoCreateState = _ngoCreateState.asStateFlow()

    val _getProfileState = MutableStateFlow<GetNgoProfileState>(GetNgoProfileState.Idle)
    val getProfileState = _getProfileState.asStateFlow()

    private val _getAllNgoState = MutableStateFlow<GetAllNgoState>(GetAllNgoState.Idle)
    val getAllNgoState = _getAllNgoState.asStateFlow()

    private val _getSingleNgoState = MutableStateFlow<GetSingleNgoState>(GetSingleNgoState.Idle)
    val getSingleNgoState = _getSingleNgoState.asStateFlow()


    fun createNgoProfile(
        name: String,
        description: String,
        phone: String,
        city: String,
        address: String,
        website: String,
        logo: MultipartBody.Part?
    ) {

        _ngoCreateState.value = NgoCreateState.Loading

        viewModelScope.launch {
            try {
                val response = repo.createNgo(
                    name,
                    description,
                    phone,
                    city,
                    address,
                    website,
                    logo
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        _ngoCreateState.value = NgoCreateState.Success(it)
                    }

                } else {
                    _ngoCreateState.value = NgoCreateState.Error(response.message())
                }

            } catch (e: Exception) {
                _ngoCreateState.value = NgoCreateState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun getProfile() {
        _getProfileState.value = GetNgoProfileState.Loading
        viewModelScope.launch {
            try {
                val response = repo.get_ngo_profile()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _getProfileState.value = GetNgoProfileState.Success(it)
                    }
                } else {
                    _getProfileState.value = GetNgoProfileState.Error(response.message())
                }
            } catch (e: Exception) {
                _getProfileState.value = GetNgoProfileState.Error(e.message ?: "Unknown Error")
            }

        }
    }

    fun getAllNgo(){

        _getAllNgoState.value = GetAllNgoState.Loading
        viewModelScope.launch {
            try {
                val response = repo.get_all_ngo()

               if(response.body()!=null && response.isSuccessful){
                   _getAllNgoState.value = GetAllNgoState.Success(response.body()!!.data)


                } else {
                    _getAllNgoState.value = GetAllNgoState.Error(response.message())
                }
            } catch (e: Exception) {
                _getAllNgoState.value = GetAllNgoState.Error(e.message ?: "Unknown Error")
                }

        }
    }

    fun getSingleNgo(id: Int){
        _getSingleNgoState.value = GetSingleNgoState.Loading
        viewModelScope.launch {
            try {
                val response = repo.get_single_ngo(id)

                if(response.body()!=null && response.isSuccessful){
                    _getSingleNgoState.value = GetSingleNgoState.Success(response.body()!!.data)

                } else {
                    _getSingleNgoState.value = GetSingleNgoState.Error(response.message())
                }
            } catch (e: Exception) {
                _getSingleNgoState.value = GetSingleNgoState.Error(e.message ?: "Unknown Error")
            }

                }
    }
}

