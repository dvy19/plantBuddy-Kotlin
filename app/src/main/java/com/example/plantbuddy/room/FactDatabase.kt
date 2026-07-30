package com.example.plantbuddy.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plantbuddy.room.dailyFacts.DailyFactDao
import com.example.plantbuddy.room.dailyFacts.DailyFactEntity
import com.example.plantbuddy.room.photo.OfflinePicEntity
import com.example.plantbuddy.room.photo.PicDao
import com.example.plantbuddy.room.wishlist.PlantDao
import com.example.plantbuddy.room.wishlist.PlantEntity


@Database(
    entities = [
        SavedFact::class,
        OfflinePicEntity::class,
        PlantEntity::class,
        DailyFactEntity::class
               ],
    version = 5
)
abstract class FactDatabase : RoomDatabase() {

    abstract fun savedFactDao(): FactDao

    abstract fun picDao(): PicDao

    abstract fun plantDao():PlantDao

    abstract fun dailyFactDao():DailyFactDao
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