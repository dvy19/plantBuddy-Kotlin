package com.example.plantbuddy.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantbuddy.plants.Fact
import kotlinx.coroutines.launch

class SavedFactViewModel(
    private val repository: SavedFactRepository
) : ViewModel() {

    val savedFacts = repository.savedFacts

    fun saveFact(fact: Fact) {

        viewModelScope.launch {

            repository.saveFact(
                SavedFact(
                    title = fact.title,
                    fact = fact.fact,
                    category = fact.category,
                    savedAt = System.currentTimeMillis()
            )
            )
        }
    }
}