package com.ilein.thecocktailapp.network

import com.ilein.thecocktailapp.BuildConfig
import com.ilein.thecocktailapp.network.model.Drinks
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*

class Api {
    private val baseUrl = BuildConfig.API_BASE_URL
    private val drinks = "api/json/${BuildConfig.API_VERSION}/${BuildConfig.API_KEY}/search.php"
    private val drink = "api/json/${BuildConfig.API_VERSION}/${BuildConfig.API_KEY}/lookup.php"


    private val ktorClient = HttpClient() {
        install(Logging) {
            level = LogLevel.BODY
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getDrink(drinkId: Int): Drinks = ktorClient.use {
        it.get(
            host = baseUrl,
            path = buildPath(drink, mapOf(Pair("i", drinkId)))
        )
    }

    suspend fun getDrinks(searchText: String): Drinks = ktorClient.use {
        it.get(
            host = baseUrl,
            path = buildPath(drinks, mapOf(Pair("s", searchText)))
        )
    }

    private fun buildPath(
        endpoint: String,
        query: Map<String, Any?> = emptyMap(),
    ): String =
        buildString {
            append("$endpoint?")
            append(
                query.entries.joinToString(
                    separator = "&"
                ) { (key, value) ->
                    "$key=$value"
                }
            )
        }
}