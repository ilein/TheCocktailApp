package com.ilein.thecocktailapp.domain.model

data class Drink (
    val id: Int,
    val name: String?,
    val image: String?,
    val instructions: String?,
    val ingredients: String?
)
