package com.example.plantbuddy.room.PersonalPlant


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantbuddy.room.SavedFactRepository
import com.example.plantbuddy.room.SavedFactViewModel


class PersonalPlantVMFac(
    private val repository: PersonalPlantRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonalPlantVM::class.java)) {
            return PersonalPlantVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}