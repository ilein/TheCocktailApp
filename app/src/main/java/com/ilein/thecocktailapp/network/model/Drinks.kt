package com.ilein.thecocktailapp.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer

@Serializable
data class Drinks(
    @SerialName("drinks")
    @Serializable(with = DrinkListSerializer::class)
    val results: List<Drink> = ArrayList()
)

object DrinkListSerializer : JsonTransformingSerializer<List<Drink>>(ListSerializer(Drink.serializer())) {
    // If response is not an array, then it is an empty array
    override fun transformDeserialize(element: JsonElement): JsonElement =
        if (element !is JsonArray) JsonArray(listOf()) else element
}