package com.example.plantbuddy.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantbuddy.plants.Fact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class SavedFactState{
    object Idle: SavedFactState()
    object Loading: SavedFactState()
    object Success: SavedFactState()
    data class Error(val message: String): SavedFactState()
}
class SavedFactViewModel(
    private val repository: SavedFactRepository
) : ViewModel() {

    private val _saveState = MutableStateFlow<SavedFactState>(SavedFactState.Idle)
    val saveState: StateFlow<SavedFactState> = _saveState.asStateFlow()


    val savedFacts = repository.savedFacts


        fun saveFact(fact: Fact) {
            viewModelScope.launch {
                _saveState.value = SavedFactState.Loading
                try {
                    repository.saveFact(
                        SavedFact(
                            title = fact.title,
                            fact = fact.fact,
                            category = fact.category,
                            savedAt = System.currentTimeMillis()
                        )
                    )
                    _saveState.value = SavedFactState.Success
                } catch (e: Exception) {
                    _saveState.value = SavedFactState.Error(e.localizedMessage ?: "Failed to save fact")
                }
            }
        }

        // Reset state after handling error/toast in UI
        fun resetSaveState() {
            _saveState.value = SavedFactState.Idle
        }
    }
