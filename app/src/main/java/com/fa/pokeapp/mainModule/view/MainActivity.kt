package com.fa.pokeapp.mainModule.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fa.pokeapp.common.Utils.Constants
import com.fa.pokeapp.common.dataAccess.PokeServer
import com.fa.pokeapp.common.entities.Pokemon
import com.fa.pokeapp.common.entities.PokemonDataResponse
import com.fa.pokeapp.databinding.ActivityMainBinding
import com.fa.pokeapp.mainModule.view.adapters.PokeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mRetrofit: Retrofit
    private lateinit var mAdapter: PokeAdapter
    private var isLoading by Delegates.notNull<Boolean>()
    private lateinit var pokemonsResult: PokemonDataResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mRetrofit = getRetrofit()
        initUI()
        setupAdapter()
    }

    private fun initUI() {
        mBinding.progressbar.isVisible = true
        isLoading = false
        CoroutineScope(Dispatchers.IO).launch {
            try {
                pokemonsResult = mRetrofit.create(PokeServer::class.java).getListPokemon("20", "0")
                val deferredPokemons = pokemonsResult.results.map { pokemonData ->
                    async {
                        mRetrofit.create(PokeServer::class.java).getPokemon(pokemonData.name)
                    }
                }

                val pokemons = deferredPokemons.awaitAll()
                runOnUiThread {
                    mAdapter.updateList(pokemons)
                    mBinding.progressbar.isVisible = false
                }
            } catch (e: Exception) {
                handleNetworkError(e)
            }
        }

        mBinding.svPokemon.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupAdapter() {
        mAdapter = PokeAdapter(mutableListOf()) { navigateToDetail(it) }
        mBinding.rvPokemon.setHasFixedSize(true)
        mBinding.rvPokemon.layoutManager = GridLayoutManager(this, 2)
        mBinding.rvPokemon.adapter = mAdapter

        mBinding.rvPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    loadMoreData()
                }
            }
        })
    }

    private fun loadMoreData() {
        mBinding.progressbar.isVisible = true
        isLoading = true
        val nextPageOffset = pokemonsResult.next?.substringAfter("offset=")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                pokemonsResult =
                    mRetrofit.create(PokeServer::class.java).getListPokemon("20", nextPageOffset!!)
                val deferredPokemons = pokemonsResult.results.map { pokemonData ->
                    async {
                        mRetrofit.create(PokeServer::class.java).getPokemon(pokemonData.name)
                    }
                }
                val pokemons = deferredPokemons.awaitAll()
                runOnUiThread {
                    mAdapter.updateList(pokemons)
                    mBinding.progressbar.isVisible = false
                    isLoading = false
                }
            } catch (e: Exception) {
                handleNetworkError(e)
            }
        }
    }

    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val myResponse: Pokemon = mRetrofit.create(PokeServer::class.java).getPokemon(query)
                if (myResponse != null) {
                    navigateToDetail(myResponse)
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, myResponse.name, Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity, "No existe o esta mal escrito", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                handleNetworkError(e)
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun navigateToDetail(selectedPokemon: Pokemon) {
        val intent = Intent(this@MainActivity, PokemonDetail::class.java)
        intent.putExtra(Constants.EXTRA_ID, selectedPokemon)
        startActivity(intent)
    }

    private fun handleNetworkError(e: Exception) {
        runOnUiThread {
            Toast.makeText(this@MainActivity, "Network error occurred", Toast.LENGTH_SHORT).show()
            Log.i("ERROR", "ERROR: ${e.message}")
        }
    }
}
