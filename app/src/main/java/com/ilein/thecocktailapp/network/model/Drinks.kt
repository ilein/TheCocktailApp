package com.ilein.thecocktailapp.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Drinks(
    @SerialName("drinks")
    val results: List<Drink> = ArrayList()
)