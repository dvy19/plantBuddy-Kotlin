package com.example.plantbuddy.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class SavedViewModelFac(
    private val repository: SavedFactRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedFactViewModel::class.java)) {
            return SavedFactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}