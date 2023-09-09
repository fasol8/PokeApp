package com.fa.pokeapp.mainModule.view.adapters

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.fa.pokeapp.common.utils.CommonUtils
import com.fa.pokeapp.common.entities.Pokemon
import com.fa.pokeapp.databinding.ItemPokemonBinding
import com.fa.pokeapp.mainModule.model.MainRepository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//class PokeViewHolder(private val binding:ItemPokemonBinding) : RecyclerView.ViewHolder(view) {

    val mBinding = ItemPokemonBinding.bind(view)
    private val repository = MainRepository()

    fun bind(item: Pokemon, onItemSelected: (Pokemon) -> Unit) {

//        mBinding.tvPokeName.text = item.name
        val url =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${item.id}.png"
        Picasso.get().load(url).into(mBinding.ivPokemon)
        mBinding.cardBackground.setBackgroundResource(CommonUtils.typeColor(item.types[0].type.name))

        mBinding.root.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val detailPokemon: Pokemon = repository.getFetchPokemonData(item.name)
                    if (detailPokemon != null) {
                        withContext(Dispatchers.Main) {
                            onItemSelected(detailPokemon)
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(itemView.context, "Network error occurred", Toast.LENGTH_SHORT)
                        .show()
                    Log.i("ERROR", "ERROR: ${e.message}")
                }
            }
        }
    }
}
