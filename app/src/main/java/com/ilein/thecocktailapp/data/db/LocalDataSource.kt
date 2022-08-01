package com.ilein.thecocktailapp.data.db

interface LocalDataSource {
    suspend fun insertDrinkFavorites(drinkLike: DrinkEntity)
    suspend fun getDrinkFavorites(): List<DrinkEntity>
    suspend fun getDrinkFavoritesDesc(): List<DrinkEntity>
    suspend fun getDrink(drinkId: Int): DrinkEntity?
    suspend fun deleteDrink(drinkId: Int)
}