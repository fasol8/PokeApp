package com.fa.pokeapp.mainModule.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fa.pokeapp.R
import com.fa.pokeapp.BR
import com.fa.pokeapp.common.entities.Pokemon

class PokeAdapter(
    var pokemonList: MutableList<Pokemon>,
    val onItemSelected: (Pokemon) -> Unit
) :
    RecyclerView.Adapter<PokeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokeViewHolder(layoutInflater.inflate(R.layout.item_pokemon, parent, false))
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val item = pokemonList[position]
        holder.mBinding.setVariable(BR.pokemon, item)
        holder.mBinding.executePendingBindings()
        holder.bind(item, onItemSelected)
    }

    override fun getItemCount(): Int = pokemonList.size


    fun updateList(newPokemon: List<Pokemon>?) {
        val startPosition = pokemonList.size
        if (newPokemon != null) {
            pokemonList.addAll(newPokemon)
        }
        notifyItemRangeInserted(startPosition, newPokemon?.size!!)
    }
}
