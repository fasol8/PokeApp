package com.fa.pokeapp.common.dataAccess

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private val retrofit = Retrofit
        .Builder()
        .baseUrl("https://superheroapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pokemonApiServer: PokeServer = retrofit.create(PokeServer::class.java)
}
