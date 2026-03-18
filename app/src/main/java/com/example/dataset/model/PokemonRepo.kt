package com.example.dataset.model

import com.example.dataset.BuildConfig

class PokemonRepository {
    private val api    = RetrofitClient.instance
    private val apiKey = BuildConfig.API_KEY
    private val execUrl = BuildConfig.EXEC_URL

    suspend fun getAllPokemon() = api.getAllPokemon(execUrl, apiKey)
    suspend fun getByType(type: String) = api.getPokemonByType(execUrl, apiKey, type = type)
    suspend fun getByName(name: String) = api.getPokemonByName(execUrl, apiKey, name = name)

    suspend fun addPokemon(pokemon: Map<String, @JvmSuppressWildcards Any>): ApiResponse<Nothing> {
        val body = pokemon.toMutableMap()
        body["apiKey"] = apiKey
        body["action"] = "addPokemon"
        return api.addPokemon(execUrl, body)
    }

    suspend fun addFavorite(name: String, type1: String, num: Int): ApiResponse<Nothing> {
        return api.addFavorite(execUrl, mapOf(
            "apiKey"  to apiKey,
            "action"  to "addFavorite",
            "Name"    to name,
            "Type1"   to type1,
            "Num"     to num,
            "AddedBy" to "android_app"
        ))
    }
}