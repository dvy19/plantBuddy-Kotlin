package com.example.plantbuddy.userDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantbuddy.room.SavedFactRepository
import com.example.plantbuddy.room.SavedFactViewModel


class UserDetailVMFac(
    private val repository: UserDetailRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}