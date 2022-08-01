package com.ilein.thecocktailapp.presentation.ui.state

import com.ilein.thecocktailapp.domain.Drink
import com.ilein.thecocktailapp.domain.FavoriteDrink

data class DrinksSearchState (
    val input: String = "",
    val itemsMap: Map<Int, DrinkData> = emptyMap(),
    val loading: Boolean = false,
    val isOnline: Boolean = true
)

data class DrinkItemState (
    val item: DrinkData?,
    val loading: Boolean = false,
    val isOnline: Boolean = true
)

data class DrinkItemLikeState (
    val drink: Drink?,
    val drinkLike: FavoriteDrink?,
    val loading: Boolean = false,
    val isOnline: Boolean = true
)

data class DrinkData (
    val drink: Drink,
    var like: Boolean?
)

data class DrinksLikeState (
    val items: List<FavoriteDrink> = emptyList(),
    val loading: Boolean = false
)