package com.ilein.thecocktailapp.domain.usecase

import com.ilein.thecocktailapp.domain.repository.DrinkRepository

class GetDrinkUseCase(private val drinksRepository: DrinkRepository) {
    suspend operator fun invoke(drinkId: Int) = drinksRepository.requestDrink(drinkId)
}