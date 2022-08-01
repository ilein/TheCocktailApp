package com.ilein.thecocktailapp.domain.usecase

import com.ilein.thecocktailapp.domain.FavoriteDrink
import com.ilein.thecocktailapp.domain.DrinkRepository
import com.ilein.thecocktailapp.domain.base.SuspendUseCaseBase
import kotlinx.coroutines.CoroutineDispatcher

class GetDrinkFavoritesUseCase(
    private val repository: DrinkRepository,
    dispatcher: CoroutineDispatcher
): SuspendUseCaseBase<String, List<FavoriteDrink>>(dispatcher) {
    override suspend fun execute(arg: String): List<FavoriteDrink> {
      return repository.getDrinkFavorites()
    }
}