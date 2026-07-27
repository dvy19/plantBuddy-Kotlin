package com.example.plantbuddy.room.photo

import com.example.plantbuddy.room.SavedFactRepository
import com.example.plantbuddy.room.SavedFactViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class PicViewModelFact(
    private val repository: PicRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PicViewModel::class.java)) {
            return PicViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}