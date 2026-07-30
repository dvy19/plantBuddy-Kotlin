package com.example.plantbuddy.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantbuddy.waterStreak.WaterLogRepo
import com.example.plantbuddy.waterStreak.WaterViewModel


class WaterStreakVMFactory(
    private val repository: WaterLogRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterViewModel::class.java)) {
            return WaterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}