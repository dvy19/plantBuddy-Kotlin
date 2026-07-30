package com.example.plantbuddy.userDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantbuddy.auth.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


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
class DetailViewModel (
    val repo: UserDetailRepo
) : ViewModel() {

    private val _createProfileState = MutableStateFlow< CreateProfileState>(CreateProfileState.Idle)
    val createProfileState: StateFlow<CreateProfileState> = _createProfileState

    private val _getProfileState = MutableStateFlow<GetProfileState>(GetProfileState.Idle)
    val getProfileState: StateFlow<GetProfileState> = _getProfileState

    fun createUserProfile(request: UserDetailReq){

        _createProfileState.value = CreateProfileState.Loading

        viewModelScope.launch{

            try {
                val response = repo.create_user_profile(request)

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

                if(response.isSuccessful && response.body()!=null){
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





}







