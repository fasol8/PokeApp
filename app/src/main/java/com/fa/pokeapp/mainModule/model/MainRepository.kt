package com.fa.pokeapp.mainModule.model

import com.fa.pokeapp.common.entities.Pokemon
import com.fa.pokeapp.common.entities.PokemonDataResponse

class MainRepository {
    private val remoteDatabase = RemoteDatabase()

    suspend fun getFetchPokemonList(limit: String, offset: String): PokemonDataResponse =
        remoteDatabase.getPokemonList(limit, offset)

    suspend fun getFetchPokemonData(pokemonName: String): Pokemon =
        remoteDatabase.getPokemonData(pokemonName)
}