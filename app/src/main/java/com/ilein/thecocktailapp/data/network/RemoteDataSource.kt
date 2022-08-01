package com.ilein.thecocktailapp.data.network

import com.ilein.thecocktailapp.domain.Drink
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun searchDrinks(query: String): Flow<List<Drink>>
    fun getDrink(drinkId: Int): Flow<Drink>
}