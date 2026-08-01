package com.example.plantbuddy.NGO.details

import com.example.plantbuddy.room.SavedFactRepository
import com.example.plantbuddy.room.SavedFactViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class NgoDetailsVmFac(
    private val repository: NgoDetailsRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NgoDetailsVM::class.java)) {
            return NgoDetailsVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}