package com.ilein.thecocktailapp.domain.usecase

import com.ilein.thecocktailapp.domain.DrinkRepository
import com.ilein.thecocktailapp.domain.base.SuspendUseCaseBase
import kotlinx.coroutines.CoroutineDispatcher

class DeleteDrinkUseCase(
    private val repository: DrinkRepository,
    dispatcher: CoroutineDispatcher
): SuspendUseCaseBase<Int, Unit>(dispatcher) {
    override suspend fun execute(arg: Int) {
      repository.deleteFavoriteDrink(arg)
    }
}