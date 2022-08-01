package com.ilein.thecocktailapp.data.network

import com.ilein.thecocktailapp.data.network.model.DrinksModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class DrinksReq {

    fun requestDrinks(query: String): Flow<DrinksModel> {
        return requestDrinksDr(query)
            .flowOn(Dispatchers.IO)
            .retryWithDelay()
    }

    fun requestDrink(drinkId: Int): Flow<DrinksModel> {
        return requestDrinkDr(drinkId)
            .flowOn(Dispatchers.IO)
            .retryWithDelay()
    }

    /* additional */

    private fun requestDrinksDr(query: String): Flow<DrinksModel> {
        return flow {
            emit(
                ClientProvider().searchDrinks(query)
            )
        }
    }

    private fun requestDrinkDr(drinkId: Int): Flow<DrinksModel> {
        return flow {
            emit(
                ClientProvider().getDrink(drinkId)
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