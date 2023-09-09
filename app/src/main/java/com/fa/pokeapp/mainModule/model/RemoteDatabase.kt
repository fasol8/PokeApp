package com.fa.pokeapp.mainModule.model

import com.fa.pokeapp.common.dataAccess.PokeServer
import com.fa.pokeapp.common.entities.Pokemon
import com.fa.pokeapp.common.entities.PokemonDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDatabase {
    private val retrofit = Retrofit
        .Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(PokeServer::class.java)

    suspend fun getPokemonList(limit: String, offset: String): PokemonDataResponse =
        withContext(Dispatchers.IO) {
            service.getListPokemon(limit, offset)
        }

    suspend fun getPokemonData(pokemonName: String): Pokemon = withContext(Dispatchers.IO) {
        service.getPokemon(pokemonName)
    }
}