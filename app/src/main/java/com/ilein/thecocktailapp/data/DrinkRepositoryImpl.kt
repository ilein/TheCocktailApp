package com.ilein.thecocktailapp.data

import com.ilein.thecocktailapp.data.db.LocalDataSource
import com.ilein.thecocktailapp.data.network.RemoteDataSource
import com.ilein.thecocktailapp.domain.Drink
import com.ilein.thecocktailapp.domain.FavoriteDrink
import com.ilein.thecocktailapp.domain.DrinkRepository
import kotlinx.coroutines.flow.Flow

class DrinkRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): DrinkRepository {
    override suspend fun insertDrinkFavorites(drink: FavoriteDrink) =
        localDataSource.insertDrinkFavorites(drink.toEntity())

    override suspend fun getDrinkFavorites(): List<FavoriteDrink> =
        localDataSource.getDrinkFavorites().map { it.toDomain() }

    override suspend fun getDrinkFavoritesDesc(): List<FavoriteDrink> =
        localDataSource.getDrinkFavoritesDesc().map { it.toDomain() }

    override suspend fun getFavoriteDrink(drinkId: Int): FavoriteDrink? =
        localDataSource.getDrink(drinkId)?.toDomain()

    override suspend fun deleteFavoriteDrink(drinkId: Int) =
        localDataSource.deleteDrink(drinkId)

    override suspend fun getDrink(drinkId: Int): Flow<Drink> =
        remoteDataSource.getDrink(drinkId)

    override suspend fun searchDrinks(query: String): Flow<List<Drink>> =
        remoteDataSource.searchDrinks(query)
}