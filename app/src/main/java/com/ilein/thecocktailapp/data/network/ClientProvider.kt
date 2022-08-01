package com.ilein.thecocktailapp.data.network

import com.ilein.thecocktailapp.BuildConfig
import com.ilein.thecocktailapp.data.network.model.DrinksModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ClientProvider {
    private val baseUrl = BuildConfig.API_BASE_URL
    private val drinks = "api/json/${BuildConfig.API_VERSION}/${BuildConfig.API_KEY}/search.php"
    private val drink = "api/json/${BuildConfig.API_VERSION}/${BuildConfig.API_KEY}/lookup.php"


    private val ktorClient = HttpClient() {
        install(Logging) {
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(Json{ ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true})
        }
        install(HttpCache)
    }

    suspend fun getDrink(drinkId: Int): DrinksModel = ktorClient.use {
        it.get { host = baseUrl
            url(port = 8080, path = buildPath(drink, mapOf(Pair("i", drinkId)))) }.body()
    }

    suspend fun searchDrinks(searchText: String): DrinksModel = ktorClient.use {
        it.get { host = baseUrl
            url(port = 8080, path = buildPath(drinks, mapOf(Pair("s", searchText)))) }.body()
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