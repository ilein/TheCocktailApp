package com.ilein.thecocktailapp.domain.model

import java.time.LocalDateTime

data class DrinkLike (
    val id: Int,
    val name: String?,
    val image: String?,
    val createDate: LocalDateTime?,
    val note: String?
)