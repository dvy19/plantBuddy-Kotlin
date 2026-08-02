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
class NgoDetailsVM(
    private val repo: NgoDetailsRepo
) : ViewModel() {

    val _ngoCreateState = MutableStateFlow<NgoCreateState>(NgoCreateState.Idle)
    val ngoCreateState = _ngoCreateState.asStateFlow()

    val _getProfileState = MutableStateFlow<GetNgoProfileState>(GetNgoProfileState.Idle)
    val getProfileState = _getProfileState.asStateFlow()


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

}
