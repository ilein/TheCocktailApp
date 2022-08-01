package com.ilein.thecocktailapp.domain.usecase

import com.ilein.thecocktailapp.domain.Drink
import com.ilein.thecocktailapp.domain.DrinkRepository
import com.ilein.thecocktailapp.domain.base.SuspendUseCaseBase
import kotlinx.coroutines.CoroutineDispatcher

class GetOneDrinkUseCase(
    private val repository: DrinkRepository,
    dispatcher: CoroutineDispatcher
): SuspendUseCaseBase<Int, Drink?>(dispatcher) {
    override suspend fun execute(arg: Int): Drink? {
      return repository.getOneDrink(arg)
    }
}