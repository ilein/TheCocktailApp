package com.ilein.thecocktailapp.domain

data class Drink (
    val id: Int,
    val name: String?,
    val image: String?,
    val instruction: String?,
    val ingredients: String?
)