package com.fa.pokeapp.mainModule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fa.pokeapp.common.entities.Pokemon
import com.fa.pokeapp.mainModule.model.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class PokeViewModel : ViewModel() {
    private val repository = MainRepository()

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    private val _pokemonData = MutableLiveData<Pokemon>()
    val pokemonData: LiveData<Pokemon> = _pokemonData

    private val _networkError = MutableLiveData<String>()
    val networkError: LiveData<String> = _networkError

    private var currentPageOffset = 0
    private var isLoading = false

    fun fetchPokemonList(limit: String, offset: String) {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getFetchPokemonList(limit, offset)
                val deferredPokemons = response.results.map { pokeData ->
                    async {
                        repository.getFetchPokemonData(pokeData.name)
                    }
                }
                val pokemons = deferredPokemons.awaitAll()
                _pokemonList.postValue(pokemons)
                currentPageOffset = offset.toInt() + pokemons.size
                isLoading = false
            } catch (e: Exception) {
                handleNetworkError(e)
                isLoading = false
            }
        }
    }

    fun fetchPokemonData(pokemonName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getFetchPokemonData(pokemonName)
                if (response != null) {
                    _pokemonData.postValue(response)
                }
            } catch (e: Exception) {
                handleNetworkError(e)
            }
        }
    }

    private fun handleNetworkError(e: Exception) {
        _networkError.postValue("Network error occurred: ${e.message}")
    }

    fun getCurrentOffset(): Int {
        return currentPageOffset
    }
}