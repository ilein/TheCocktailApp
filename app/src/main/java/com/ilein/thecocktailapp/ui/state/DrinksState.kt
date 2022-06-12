package com.ilein.thecocktailapp.ui.state

import com.ilein.thecocktailapp.db.DrinkLikeEntity
import com.ilein.thecocktailapp.network.model.Drink

data class DrinksSearchState (
    val input: String = "",
    val itemsMap: Map<Int, DrinkData> = emptyMap(),
    val loading: Boolean = false
)

data class DrinkItemState (
    val item: DrinkData?,
    val loading: Boolean = false
)

data class DrinkItemLikeState (
    val drink: Drink?,
    val drinkLike: DrinkLikeEntity?,
    val loading: Boolean = false
)

data class DrinkData (
    val drink: Drink,
    var like: Boolean?
)

data class DrinksLikeState (
    val items: List<DrinkLikeEntity> = emptyList(),
    val loading: Boolean = false
)