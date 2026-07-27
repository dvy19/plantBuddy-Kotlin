package com.example.plantbuddy.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plantbuddy.room.photo.OfflinePicEntity
import com.example.plantbuddy.room.photo.PicDao


@Database(
    entities = [
        SavedFact::class,
        OfflinePicEntity::class


               ],
    version = 2
)
abstract class FactDatabase : RoomDatabase() {

    abstract fun savedFactDao(): FactDao

    abstract fun picDao(): PicDao
}

object DatabaseProvider {

    @Volatile
    private var INSTANCE: FactDatabase? = null

    fun getDatabase(context: Context): FactDatabase {

        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context,
                FactDatabase::class.java,
                "plant_buddy.db"
            ).fallbackToDestructiveMigration()
                .build()

            INSTANCE = instance
            instance
        }
    }
}