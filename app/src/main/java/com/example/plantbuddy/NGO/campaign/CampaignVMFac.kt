package com.example.plantbuddy.NGO.campaign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CampaignVMFac(
    private val repository: CampaignRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CampaignVM::class.java)) {
            return CampaignVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}