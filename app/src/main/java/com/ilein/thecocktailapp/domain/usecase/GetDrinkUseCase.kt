package com.ilein.thecocktailapp.domain.usecase

import com.ilein.thecocktailapp.domain.Drink
import com.ilein.thecocktailapp.domain.DrinkRepository
import com.ilein.thecocktailapp.domain.base.SuspendUseCaseBase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetDrinkUseCase (
    private val repository: DrinkRepository,
    dispatcher: CoroutineDispatcher
): SuspendUseCaseBase<Int, Flow<Drink>>(dispatcher) {
    override suspend fun execute(arg: Int): Flow<Drink> {
        return repository.getDrink(arg)
    }
}