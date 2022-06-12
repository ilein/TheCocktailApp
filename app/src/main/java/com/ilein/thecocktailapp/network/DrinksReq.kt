package com.ilein.thecocktailapp.network

import com.ilein.thecocktailapp.network.model.Drinks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class DrinksReq {

    fun requestDrinks(query: String): Flow<Drinks> {
        return requestDrinksDr(query)
            .flowOn(Dispatchers.IO)
            .retryWithDelay()
    }

    fun requestDrink(drinkId: Int): Flow<Drinks> {
        return requestDrinkDr(drinkId)
            .flowOn(Dispatchers.IO)
            .retryWithDelay()
    }

    /* additional */

    private fun requestDrinksDr(query: String): Flow<Drinks> {
        return flow {
            emit(
                Api().getDrinks(query)
            )
        }
    }

    private fun requestDrinkDr(drinkId: Int): Flow<Drinks> {
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