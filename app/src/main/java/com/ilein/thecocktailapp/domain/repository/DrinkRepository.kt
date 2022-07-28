package com.ilein.thecocktailapp.domain.repository

import com.ilein.thecocktailapp.domain.model.Drinks
import kotlinx.coroutines.flow.Flow

public interface DrinkRepository {
    fun requestDrinks(query: String): Flow<Drinks>

    fun requestDrink(drinkId: Int): Flow<Drinks>
}