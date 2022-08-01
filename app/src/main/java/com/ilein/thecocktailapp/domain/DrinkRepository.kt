package com.ilein.thecocktailapp.domain

import kotlinx.coroutines.flow.Flow


interface DrinkRepository {
    suspend fun insertDrinkFavorites(drink: FavoriteDrink)
    suspend fun getDrinkFavorites(): List<FavoriteDrink>
    suspend fun getDrinkFavoritesDesc(): List<FavoriteDrink>
    suspend fun getFavoriteDrink(drinkId: Int): FavoriteDrink?
    suspend fun deleteFavoriteDrink(drinkId: Int)
    suspend fun getDrink(drinkId: Int): Flow<Drink>
    suspend fun searchDrinks(query: String): Flow<List<Drink>>
}