package com.ilein.thecocktailapp.domain.usecase

import com.ilein.thecocktailapp.domain.FavoriteDrink
import com.ilein.thecocktailapp.domain.DrinkRepository
import com.ilein.thecocktailapp.domain.base.SuspendUseCaseBase
import kotlinx.coroutines.CoroutineDispatcher

class InsertDrinkUseCase(
    private val repository: DrinkRepository,
    dispatcher: CoroutineDispatcher
): SuspendUseCaseBase<FavoriteDrink, Unit>(dispatcher) {
    override suspend fun execute(arg: FavoriteDrink) {
      repository.insertDrinkFavorites(arg)
    }
}