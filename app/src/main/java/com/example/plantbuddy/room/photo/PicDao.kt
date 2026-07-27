package com.example.plantbuddy.room.photo

import androidx.room.Dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface PicDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPic(pic: OfflinePicEntity)


}