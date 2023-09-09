package com.fa.pokeapp.mainModule.viewmodel

import androidx.lifecycle.ViewModel
import com.fa.pokeapp.common.utils.CommonUtils
import com.fa.pokeapp.common.entities.Pokemon

class PokeDetailViewModel(private val pokemon: Pokemon) : ViewModel() {

    val name: String = pokemon.name
    val weight: String = "Weight: ${pokemon.weight.toString()}"
    val height: String = "Height: ${pokemon.height.toString()}"
    val tvPokeId: String = "# "+ CommonUtils.getIdNumber(pokemon.id)
    val tvHP: String = pokemon.stats[0].base_stat.toString()
    val tvAttack: String = pokemon.stats[1].base_stat.toString()
    val tvDefense: String = pokemon.stats[2].base_stat.toString()
    val tvAttackSpecial: String = pokemon.stats[3].base_stat.toString()
    val tvDefenseSpecial: String = pokemon.stats[4].base_stat.toString()
    val tvSpeed: String = pokemon.stats[5].base_stat.toString()

}