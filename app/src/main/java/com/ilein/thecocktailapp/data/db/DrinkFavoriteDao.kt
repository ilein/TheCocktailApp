package com.ilein.thecocktailapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class DrinkFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertDrink(drinkFavorite: DrinkEntity)

    @Query("SELECT * FROM drink_like")
    abstract suspend fun getDrinkFavorites(): List<DrinkEntity>

    @Query("SELECT * FROM drink_like order by update_date desc")
    abstract suspend fun getDrinkFavoritesDesc(): List<DrinkEntity>

    @Query("SELECT * FROM drink_like WHERE id = :drinkId")
    abstract suspend fun getDrink(drinkId: Int): DrinkEntity?

    @Query("DELETE FROM drink_like WHERE id = :drinkId")
    abstract suspend fun deleteDrink(drinkId: Int)
}