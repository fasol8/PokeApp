package com.fa.pokeapp.common.entities

import retrofit2.Response

data class PokemonDataResponse(
    val count: Int,
    val next: String?,
    val previous: String,
    val results: List<PokemonItemResult>
)

data class PokemonItemResult(
    val name: String,
    val url: String
)