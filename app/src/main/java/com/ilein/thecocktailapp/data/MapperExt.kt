package com.ilein.thecocktailapp.data

import com.ilein.thecocktailapp.data.db.DrinkEntity
import com.ilein.thecocktailapp.data.network.model.DrinkModel
import com.ilein.thecocktailapp.data.network.model.DrinksModel
import com.ilein.thecocktailapp.domain.Drink
import com.ilein.thecocktailapp.domain.FavoriteDrink

fun FavoriteDrink.toEntity() = DrinkEntity(
        id = this.id,
        name = this.name,
        image = this.image,
        createDate = this.createDate,
        note = this.note
)

fun DrinkEntity.toDomain() = FavoriteDrink(
    id = this.id,
    name = this.name,
    image = this.image,
    createDate = this.createDate,
    note = this.note
)

fun DrinkModel.toDomain() = Drink(
    id = this.idDrink,
    name = this.strDrink,
    image = this.strDrinkThumb,
    ingredients = this.getIngredientsText(),
    instruction = this.strInstructions
)