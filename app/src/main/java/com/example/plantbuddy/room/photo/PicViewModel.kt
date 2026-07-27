package com.example.plantbuddy.room.photo





import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class SavedPicState{
    object Idle: SavedPicState()
    object Loading: SavedPicState()
    object Success: SavedPicState()
    data class Error(val message: String): SavedPicState()
}


class PicViewModel(
    private val repository: PicRepository
) : ViewModel() {

    private val _savePicState = MutableStateFlow<SavedPicState>(SavedPicState.Idle)
    val savePicState: StateFlow<SavedPicState> = _savePicState.asStateFlow()




    val savedPics = repository.getAllSavedPics()


    fun savePic(pic: OfflinePicEntity) {
        viewModelScope.launch {
            _savePicState.value = SavedPicState.Loading
            try {
                repository.savePic(
                  pic = OfflinePicEntity(
                      imagePath = pic.imagePath,
                      note = pic.note,
                      plant_name = pic.plant_name,
                      savedAt = System.currentTimeMillis(),
                      mood = pic.mood,
                      date = pic.date,
                      time = pic.time
                  )
                )
                _savePicState.value = SavedPicState.Success
            } catch (e: Exception) {
                _savePicState.value = SavedPicState.Error(e.localizedMessage ?: "Failed to save fact")
            }
        }
    }

    // Reset state after handling error/toast in UI
    fun resetSaveState() {
        _savePicState.value = SavedPicState.Idle
    }
}
