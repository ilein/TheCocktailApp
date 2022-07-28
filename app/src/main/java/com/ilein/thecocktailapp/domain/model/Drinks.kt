package com.ilein.thecocktailapp.domain.model

import java.util.*

data class Drinks (
    val drinks: List<Drink>
) {
    constructor(): this(Collections.emptyList())
}