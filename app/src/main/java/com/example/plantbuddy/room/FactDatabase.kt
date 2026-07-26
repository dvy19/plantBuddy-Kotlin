package com.example.plantbuddy.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [SavedFact::class],
    version = 1
)
abstract class FactDatabase : RoomDatabase() {

    abstract fun savedFactDao(): FactDao
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
            ).build()

            INSTANCE = instance
            instance
        }
    }
}