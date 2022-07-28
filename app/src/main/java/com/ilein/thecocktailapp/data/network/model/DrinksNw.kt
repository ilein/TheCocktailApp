package com.ilein.thecocktailapp.data.network.model

import com.ilein.thecocktailapp.domain.model.Drinks
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlin.streams.toList

@Serializable
class DrinksNw(
    @SerialName("drinks")
    @Serializable(with = DrinkListSerializer::class)
    val results: List<DrinkNw> = ArrayList()) {
    fun  toDomainModel(): Drinks {
        return Drinks(results.stream().map{ t -> t.toDomainModel()}.toList())
    }

    object DrinkListSerializer : JsonTransformingSerializer<List<DrinkNw>>(ListSerializer(DrinkNw.serializer())) {
        // If response is not an array, then it is an empty array
        override fun transformDeserialize(element: JsonElement): JsonElement =
            if (element !is JsonArray) JsonArray(listOf()) else element
    }
}