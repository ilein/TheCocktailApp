package com.ilein.thecocktailapp.domain.usecase

import com.ilein.thecocktailapp.domain.Drink
import com.ilein.thecocktailapp.domain.DrinkRepository
import com.ilein.thecocktailapp.domain.base.SuspendUseCaseBase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class SearchDrinkUseCase (
    private val repository: DrinkRepository,
    dispatcher: CoroutineDispatcher
): SuspendUseCaseBase<String, Flow<List<Drink>>>(dispatcher) {
    override suspend fun execute(arg: String): Flow<List<Drink>> {
        return repository.searchDrinks(arg)
    }
}