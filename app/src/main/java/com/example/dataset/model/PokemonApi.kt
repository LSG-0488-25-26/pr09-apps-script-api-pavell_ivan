package com.example.dataset.model

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApiService {

    @GET
    suspend fun getAllPokemon(
        @Url url: String,
        @Query("apiKey") apiKey: String,
        @Query("action") action: String = "getAll"
    ): ApiResponse<Pokemon>

    @GET
    suspend fun getPokemonByType(
        @Url url: String,
        @Query("apiKey") apiKey: String,
        @Query("action") action: String = "getByType",
        @Query("type") type: String
    ): ApiResponse<Pokemon>

    @GET
    suspend fun getPokemonByName(
        @Url url: String,
        @Query("apiKey") apiKey: String,
        @Query("action") action: String = "getByName",
        @Query("name") name: String
    ): ApiResponse<Pokemon>

    @POST
    suspend fun addPokemon(
        @Url url: String,
        @Body body: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<Nothing>

    @POST
    suspend fun addFavorite(
        @Url url: String,
        @Body body: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<Nothing>
}