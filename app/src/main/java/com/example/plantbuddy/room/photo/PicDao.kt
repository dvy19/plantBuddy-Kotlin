package com.example.plantbuddy.room.photo

import androidx.room.Dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PicDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPic(pic: OfflinePicEntity)

    @Query("SELECT * FROM pic_table ORDER BY id DESC")
    fun getAllPics(): Flow<List<OfflinePicEntity>>


}