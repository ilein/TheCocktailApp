package com.ilein.thecocktailapp.data.network

import com.ilein.thecocktailapp.data.network.model.DrinksModel
import com.ilein.thecocktailapp.data.toDomain
import com.ilein.thecocktailapp.domain.Drink
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class RemoteDataSourceImpl(
    private val client: ClientProvider,
    private val ioDispatcher: CoroutineDispatcher
): RemoteDataSource {

    override fun searchDrinks(query: String): Flow<List<Drink>> {
        return flow { emit(client.searchDrinks(query).results.map { t -> t.toDomain() }) }
            .flowOn(ioDispatcher)
            .retryWithDelay()
    }

    override fun getDrink(drinkId: Int): Flow<Drink> {
        return flow { emit(client.getDrink(drinkId).results.first().toDomain()) }
            .flowOn(ioDispatcher)
            .retryWithDelay()
    }

    private fun <T> Flow<T>.retryWithDelay(
        delay: Long = DEFAULT_DELAY,
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

    companion object {
        private const val DEFAULT_DELAY = 2000L
    }
}