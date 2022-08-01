package com.ilein.thecocktailapp.domain


import java.time.LocalDateTime

data class FavoriteDrink(
    val id: Int,
    val name: String?,
    val image: String?,
    val createDate: LocalDateTime?,
    val note: String?,
)
