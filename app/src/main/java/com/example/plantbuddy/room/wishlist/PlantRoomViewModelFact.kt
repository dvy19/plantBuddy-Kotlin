package com.example.plantbuddy.room.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlantRoomViewModelFact(private val repository: PlantRoomRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantRoomViewModel::class.java)) {
            return PlantRoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}