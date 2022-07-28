package com.ilein.thecocktailapp.domain.usecase

import com.ilein.thecocktailapp.domain.repository.DrinkRepository

class GetDrinksUseCase(private val drinksRepository: DrinkRepository) {
    suspend operator fun invoke(query: String) = drinksRepository.requestDrinks(query)
}