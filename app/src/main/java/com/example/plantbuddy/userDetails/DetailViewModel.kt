package com.example.plantbuddy.userDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantbuddy.auth.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody


sealed class CreateProfileState{
    object Idle : CreateProfileState()
    object Loading : CreateProfileState()
    data class Success(val data: Userdata) : CreateProfileState()
    data class Error(val message: String) : CreateProfileState()
}

sealed class GetProfileState{
    object Idle : GetProfileState()
    object Loading : GetProfileState()
    data class Success(val data: Userdata) : GetProfileState()
    data class Error(val message: String) : GetProfileState()

}

sealed class GetVolunteerProfileState{
    object Idle : GetVolunteerProfileState()
    object Loading : GetVolunteerProfileState()
    data class Success(val data: VolunteerProfile) : GetVolunteerProfileState()
    data class Error(val message: String) : GetVolunteerProfileState()
}

sealed class CreateVolunteerProfileState{
    object Idle : CreateVolunteerProfileState()
    object Loading : CreateVolunteerProfileState()
    data class Success(val data: VolunteerProfile) : CreateVolunteerProfileState()
    data class Error(val message: String) : CreateVolunteerProfileState()
}


class DetailViewModel (
    val repo: UserDetailRepo
) : ViewModel() {

    private val _createProfileState = MutableStateFlow< CreateProfileState>(CreateProfileState.Idle)
    val createProfileState: StateFlow<CreateProfileState> = _createProfileState

    private val _getProfileState = MutableStateFlow<GetProfileState>(GetProfileState.Idle)
    val getProfileState: StateFlow<GetProfileState> = _getProfileState.asStateFlow()

    private val _createVolunteerProfileState = MutableStateFlow<CreateVolunteerProfileState>(CreateVolunteerProfileState.Idle)
    val createVolunteerProfileState: StateFlow<CreateVolunteerProfileState> = _createVolunteerProfileState.asStateFlow()


    private val _getVolunteerProfileState = MutableStateFlow<GetVolunteerProfileState>(GetVolunteerProfileState.Idle)
    val getVolunteerProfileState: StateFlow<GetVolunteerProfileState> = _getVolunteerProfileState.asStateFlow()


    fun createUserProfile(request: UserDetailReq){

        _createProfileState.value = CreateProfileState.Loading

        viewModelScope.launch{

            try {
                val response = repo.create_user_profile(request)

                println(response)

                println(response.body())

                println(response.isSuccessful)

                println(response.code())
                println(response.errorBody())
                if(response.isSuccessful && response.body()!=null){

                    _createProfileState.value = CreateProfileState.Success(response.body()!!.data)

                }
                else{
                    throw Exception("Failed to create user profile")
                }

            } catch (e: Exception) {
                _createProfileState.value = CreateProfileState.Error(e.message ?: "An error occurred")
            }
            }
        }


    fun getUserProfile(){

        _getProfileState.value = GetProfileState.Loading

        viewModelScope.launch{

            try {
                val response = repo.get_user_profile()

                Log.d("m",response.toString())
                println(response)
                println(response.body())
                println(response.isSuccessful)
                println(response.code())
                println(response.errorBody())

                if(response.isSuccessful && response.body()!=null){
                    Log.d("m",response.toString())
                    Log.d("m",response.body().toString())
                    Log.d("isSucess",response.isSuccessful.toString())
                    Log.d("m",response.code().toString())
                    Log.d("m",response.errorBody().toString())
                    _getProfileState.value = GetProfileState.Success(response.body()!!.data)
                }
                else{
                    throw Exception("Failed to get user profile")
                }
                } catch (e: Exception) {
                    _getProfileState.value = GetProfileState.Error(e.message ?: "An error occurred")
                }
        }
    }



    fun createVolunteerProfile(
        name:String,
        phone:String,
        city:String,
        gender:String,
        image:MultipartBody.Part?
    ){
        _createVolunteerProfileState.value = CreateVolunteerProfileState.Loading
        viewModelScope.launch {

            try {
                val response = repo.create_volunteer_profile(
                    name,
                    phone,
                    city,
                    gender,
                    image
                )

                println(response)

                println(response.body())


                println(response.isSuccessful)

                println(response.code())
                println(response.errorBody())


                if(response.isSuccessful && response.body()!=null){
                    _createVolunteerProfileState.value = CreateVolunteerProfileState.Success(response.body()!!.data)
                }
                else{
                    throw Exception("Failed to create volunteer profile")
                }
                } catch (e: Exception) {
                    _createVolunteerProfileState.value = CreateVolunteerProfileState.Error(e.message ?: "An error occurred")
                }

        }

    }

    fun getVolunteerProfile(){
        _getVolunteerProfileState.value = GetVolunteerProfileState.Loading
        viewModelScope.launch {
            try {
                val response = repo.get_volunteer_profile()
                println(response)
                println(response.body())
                println(response.isSuccessful)
                println(response.code())
                println(response.errorBody())
                if(response.isSuccessful && response.body()!=null){
                    _getVolunteerProfileState.value = GetVolunteerProfileState.Success(response.body()!!.data)
                }
                else{
                    throw Exception("Failed to get volunteer profile")


                }
            } catch (e: Exception) {
                _getVolunteerProfileState.value = GetVolunteerProfileState.Error(e.message ?: "An error occurred")
            }
        }
    }




    }







