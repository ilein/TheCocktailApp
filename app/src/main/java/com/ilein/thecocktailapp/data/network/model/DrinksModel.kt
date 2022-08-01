package com.ilein.thecocktailapp.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer

@Serializable
data class DrinksModel(
    @SerialName("drinks")
    @Serializable(with = DrinkListSerializer::class)
    val results: List<DrinkModel> = ArrayList()
) {

    object DrinkListSerializer : JsonTransformingSerializer<List<DrinkModel>>(ListSerializer(DrinkModel.serializer())) {
        // If response is not an array, then it is an empty array
        override fun transformDeserialize(element: JsonElement): JsonElement =
            if (element !is JsonArray) JsonArray(listOf()) else element
    }
}