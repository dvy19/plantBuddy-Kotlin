package com.example.plantbuddy.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Response


sealed class RegisterState{
    object Idle: RegisterState()
    object Loading: RegisterState()
    data class Success(val data: SignupResponse): RegisterState()
    data class Error(val message: String): RegisterState()

}

sealed class LoginState{
    object Idle: LoginState()
    object Loading: LoginState()
    data class Success(val data: LoginResponse): LoginState()
    data class Error(val message: String): LoginState()
}
class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = AuthRepo()


    val sessionManager= SessionManager(application)

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun register(request: SignupRequest) {

       viewModelScope.launch {

           _registerState.value = RegisterState.Loading

           try {
               val response = repo.register(request)

               if(response.isSuccessful && response.body()!=null){

                       _registerState.value = RegisterState.Success(response.body()!!)

                   val tokens=response.body()!!.token

                   sessionManager.saveTokens(
                       access = tokens.access,
                       refresh = tokens.refresh
                   )

                   }

               else {
                   throw Exception("Failed to register user")
               }

           } catch (e: Exception) {
               _registerState.value = RegisterState.Error(e.message ?: "An error occurred")


           }

       }


    }

    fun login(request: LoginRequest)  {


        viewModelScope.launch{

            _loginState.value = LoginState.Loading

            try {
                val response = repo.login(request)

                if(response.isSuccessful && response.body()!=null){

                    _loginState.value = LoginState.Success(response.body()!!)

                    val tokens=response.body()!!.token

                    sessionManager.saveTokens(
                        access = tokens.access,
                        refresh = tokens.refresh
                    )

                }

                else {
                    throw Exception("Failed to register user")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "An error occurred")
            }
        }
    }



}








