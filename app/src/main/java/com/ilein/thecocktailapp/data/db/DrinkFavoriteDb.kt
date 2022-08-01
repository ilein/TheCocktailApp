package com.ilein.thecocktailapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        DrinkEntity::class
    ]
)

@TypeConverters(Converters::class)
abstract class DrinkFavoriteDb: RoomDatabase() {
    companion object {

        private const val databaseName = "drink_database"
        const val DRINK_LIKE_TABLE = "drink_like"

        fun createDatabaseInstance(context: Context): DrinkFavoriteDb {
            return Room
                .databaseBuilder(
                    context,
                    DrinkFavoriteDb::class.java,
                    databaseName
                )
                .build()
        }
    }

    abstract fun drinkFavoriteDao(): DrinkFavoriteDao
}