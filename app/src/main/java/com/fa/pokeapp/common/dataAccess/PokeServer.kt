package com.fa.pokeapp.common.dataAccess

import com.fa.pokeapp.common.entities.Pokemon
import com.fa.pokeapp.common.entities.PokemonDataResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeServer {

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") pokeName: String): Pokemon

    //    @GET("pokemon?limit=100")
    @GET("pokemon?")
    suspend fun getListPokemon(
        @Query("limit") limit: String,
        @Query("offset") offset: String
    ): PokemonDataResponse
}