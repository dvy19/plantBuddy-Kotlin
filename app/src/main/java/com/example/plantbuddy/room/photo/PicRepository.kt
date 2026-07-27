package com.example.plantbuddy.room.photo

import com.example.plantbuddy.room.FactDao
import com.example.plantbuddy.room.SavedFact
import kotlinx.coroutines.flow.Flow

class PicRepository(
    private val dao: PicDao
) {

    suspend fun savePic(pic: OfflinePicEntity) {
        dao.insertPic(pic)
    }


    fun getAllSavedPics()=dao.getAllPics()


}