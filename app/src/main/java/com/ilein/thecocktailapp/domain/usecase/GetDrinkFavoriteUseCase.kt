package com.ilein.thecocktailapp.domain.usecase

import com.ilein.thecocktailapp.domain.FavoriteDrink
import com.ilein.thecocktailapp.domain.DrinkRepository
import com.ilein.thecocktailapp.domain.base.SuspendUseCaseBase
import kotlinx.coroutines.CoroutineDispatcher

class GetDrinkFavoriteUseCase(
    private val repository: DrinkRepository,
    dispatcher: CoroutineDispatcher
): SuspendUseCaseBase<Int, FavoriteDrink?>(dispatcher) {
    override suspend fun execute(arg: Int): FavoriteDrink? {
      return repository.getFavoriteDrink(arg)
    }
}