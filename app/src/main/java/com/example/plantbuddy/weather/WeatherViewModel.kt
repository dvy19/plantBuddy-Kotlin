package com.example.plantbuddy.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class WeatherState {
    object Idle : WeatherState()
    object Loading : WeatherState()
    data class Success(val data: WeatherResponse) : WeatherState()
    data class Error(val message: String) : WeatherState()

}

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val weatherState: StateFlow<WeatherState> = _weatherState

    fun fetchWeather(city: String) {

        Log.d("WeatherVM", "fetchWeather called")


        viewModelScope.launch {

            _weatherState.value = WeatherState.Loading

            try {


                val response = repository.getWeather(city)

                Log.d("m", response.toString())

                Log.d("m",response.isSuccessful.toString())

                if (response.isSuccessful && response.body() != null) {

                    _weatherState.value =
                        WeatherState.Success(response.body()!!)

                } else {

                    _weatherState.value =
                        WeatherState.Error(response.message())

                }

            } catch (e: Exception) {

                _weatherState.value =
                    WeatherState.Error(e.localizedMessage ?: "Unknown error")

            }

        }
    }
}
