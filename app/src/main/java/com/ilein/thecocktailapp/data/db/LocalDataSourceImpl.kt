package com.ilein.thecocktailapp.data.db

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(
    private val dao: DrinkFavoriteDao,
    private val ioDispatcher: CoroutineDispatcher
): LocalDataSource {
    override suspend fun insertDrinkFavorites(drinkLike: DrinkEntity) =
        withContext(ioDispatcher) { dao.insertDrink(drinkLike) }

    override suspend fun getDrinkFavorites(): List<DrinkEntity> =
        withContext(ioDispatcher) { dao.getDrinkFavorites()}

    override suspend fun getDrinkFavoritesDesc(): List<DrinkEntity> =
        withContext(ioDispatcher) { dao.getDrinkFavoritesDesc() }

    override suspend fun getDrink(drinkId: Int): DrinkEntity? =
        withContext(ioDispatcher) { dao.getDrink(drinkId) }

    override suspend fun deleteDrink(drinkId: Int) =
        withContext(ioDispatcher) { dao.deleteDrink(drinkId) }

}