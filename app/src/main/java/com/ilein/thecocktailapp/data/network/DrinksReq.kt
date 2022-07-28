package com.ilein.thecocktailapp.data.network

import com.ilein.thecocktailapp.data.network.model.DrinksNw
import com.ilein.thecocktailapp.domain.repository.DrinkRepository
import com.ilein.thecocktailapp.domain.model.Drinks

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen


class DrinksReq: DrinkRepository {

    override fun requestDrinks(query: String): Flow<Drinks> {
        return requestDrinksDr(query)
            .flowOn(Dispatchers.IO)
            .map { t -> t.toDomainModel() }
            .retryWithDelay()
    }

    override fun requestDrink(drinkId: Int): Flow<Drinks> {
        return requestDrinkDr(drinkId)
            .flowOn(Dispatchers.IO)
            .map { t -> t.toDomainModel() }
            .retryWithDelay()
    }

    /* additional */

    private fun requestDrinksDr(query: String): Flow<DrinksNw> {
        return flow {
            emit(
                Api().getDrinks(query)
            )
        }
    }

    private fun requestDrinkDr(drinkId: Int): Flow<DrinksNw> {
        return flow {
            emit(
                Api().getDrink(drinkId)
            )
        }
    }

    private fun <T> Flow<T>.retryWithDelay(
        delay: Long = 2000L,
        filter: (Throwable) -> Boolean = { true }
    ): Flow<T> {
        return retryWhen { cause, _ ->
            if (filter(cause)) {
                delay(delay)
                true
            } else {
                throw cause
            }
        }
    }
}