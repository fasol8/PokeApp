package com.fa.pokeapp.mainModule.view.adapters

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.fa.pokeapp.common.Utils.CommonUtils
import com.fa.pokeapp.common.dataAccess.PokeServer
import com.fa.pokeapp.common.entities.Pokemon
import com.fa.pokeapp.databinding.ItemPokemonBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PokeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val mBinding = ItemPokemonBinding.bind(view)
    private lateinit var mRetrofit: Retrofit

    fun bind(item: Pokemon, onItemSelected: (Pokemon) -> Unit) {
        mRetrofit = getRetrofit()

        mBinding.tvPokeName.text = item.name
        val url =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${item.id}.png"
        Picasso.get().load(url).into(mBinding.ivPokemon)
        mBinding.cardBackground.setBackgroundResource(CommonUtils.typeColor(item.types[0].type.name))

        mBinding.root.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val detailPokemon: Pokemon =
                        mRetrofit.create(PokeServer::class.java).getPokemon(item.name)
                    if (detailPokemon != null) {
                        withContext(Dispatchers.Main) {
                            onItemSelected(detailPokemon)
                        }
                    }
                } catch (e: Exception) {
                        Toast.makeText(itemView.context, "Network error occurred", Toast.LENGTH_SHORT).show()
                        Log.i("ERROR", "ERROR: ${e.message}")
                    }
                }
            }
        }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
