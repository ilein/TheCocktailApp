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
        DrinkLikeEntity::class
    ]
)

@TypeConverters(Converters::class)
abstract class DrinkLikeDb: RoomDatabase() {
    companion object {

        private const val databaseName = "drink_database"
        const val DRINK_LIKE_TABLE = "drink_like"

        fun createDatabaseInstance(context: Context): DrinkLikeDb {
            return Room
                .databaseBuilder(
                    context,
                    DrinkLikeDb::class.java,
                    databaseName
                )
                .build()
        }
    }

    abstract fun drinkLikeDao(): DrinkLikeDao
}