package com.ilein.thecocktailapp.ui.state

import com.ilein.thecocktailapp.data.db.DrinkLikeEntity

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
    val drink: com.ilein.thecocktailapp.domain.model.Drink?,
    val drinkLike: DrinkLikeEntity?,
    val loading: Boolean = false,
    val isOnline: Boolean = true
)

data class DrinkData (
    val drink: com.ilein.thecocktailapp.domain.model.Drink,
    var like: Boolean?
)

data class DrinksLikeState (
    val items: List<DrinkLikeEntity> = emptyList(),
    val loading: Boolean = false
)