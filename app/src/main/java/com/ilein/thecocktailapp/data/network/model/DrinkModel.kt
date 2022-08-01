package com.ilein.thecocktailapp.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DrinkModel (
    @SerialName("idDrink")
    val idDrink: Int,
    @SerialName("strDrink")
    val strDrink: String? = null,
    @SerialName("strDrinkThumb")
    val strDrinkThumb: String? = null,
    @SerialName("strInstructions")
    val strInstructions: String? = null,

    @SerialName("strIngredient1")
    val strIngredient1: String? = null,
    @SerialName("strIngredient2")
    val strIngredient2: String? = null,
    @SerialName("strIngredient3")
    val strIngredient3: String? = null,
    @SerialName("strIngredient4")
    val strIngredient4: String? = null,
    @SerialName("strIngredient5")
    val strIngredient5: String? = null,
    @SerialName("strIngredient6")
    val strIngredient6: String? = null,
    @SerialName("strIngredient7")
    val strIngredient7: String? = null,
    @SerialName("strIngredient8")
    val strIngredient8: String? = null,
    @SerialName("strIngredient9")
    val strIngredient9: String? = null,
    @SerialName("strIngredient10")
    val strIngredient10: String? = null,

    @SerialName("strMeasure1")
    val strMeasure1: String? = null,
    @SerialName("strMeasure2")
    val strMeasure2: String? = null,
    @SerialName("strMeasure3")
    val strMeasure3: String? = null,
    @SerialName("strMeasure4")
    val strMeasure4: String? = null,
    @SerialName("strMeasure5")
    val strMeasure5: String? = null,
    @SerialName("strMeasure6")
    val strMeasure6: String? = null,
    @SerialName("strMeasure7")
    val strMeasure7: String? = null,
    @SerialName("strMeasure8")
    val strMeasure8: String? = null,
    @SerialName("strMeasure9")
    val strMeasure9: String? = null,
    @SerialName("strMeasure10")
    val strMeasure10: String? = null,
) {
    fun getIngredientsText(): String  = listOf(
        Pair(strIngredient1, strMeasure1),
        Pair(strIngredient2, strMeasure2),
        Pair(strIngredient3, strMeasure3),
        Pair(strIngredient4, strMeasure4),
        Pair(strIngredient5, strMeasure5),
        Pair(strIngredient6, strMeasure6),
        Pair(strIngredient7, strMeasure7),
        Pair(strIngredient8, strMeasure8),
        Pair(strIngredient9, strMeasure9),
        Pair(strIngredient10, strMeasure10))
        .filter { it.first.orEmpty().isNotEmpty() }
        .mapIndexed { index, pair ->
            "${index+1}. ${pair.first} ${pair.second?.let {"- $it"} ?: ""}"
        }
        .joinToString(separator = "\n")
}