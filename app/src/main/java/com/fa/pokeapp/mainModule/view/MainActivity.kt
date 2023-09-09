package com.fa.pokeapp.mainModule.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fa.pokeapp.BR
import com.fa.pokeapp.common.utils.Constants
import com.fa.pokeapp.common.entities.Pokemon
import com.fa.pokeapp.databinding.ActivityMainBinding
import com.fa.pokeapp.mainModule.view.adapters.PokeAdapter
import com.fa.pokeapp.mainModule.viewmodel.PokeViewModel
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: PokeAdapter
    private var isLoading by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupViewModel()
        setupObserver()
        initUI()
        setupAdapter()
    }

    private fun setupViewModel() {
//        viewModel = ViewModelProvider(this)[PokeViewModel::class.java]
        val vm: PokeViewModel by viewModels()
        mBinding.lifecycleOwner = this
        mBinding.setVariable(BR.viewModel, vm)
    }

    private fun setupObserver() {
        mBinding.viewModel?.pokemonData?.observe(this) {
            navigateToDetail(it)
        }

        mBinding.viewModel?.pokemonList?.observe(this) {
            mAdapter.updateList(it)
            mBinding.progressbar.isVisible = false
        }

        mBinding.viewModel?.networkError?.observe(this) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(this, "Pokemon not found or misspelled", Toast.LENGTH_SHORT).show()
                Log.i("ERROR", error)
            }
        }
    }

    private fun initUI() {
        mBinding.progressbar.isVisible = true
        isLoading = false
        mBinding.viewModel?.fetchPokemonList("20", "0")

        mBinding.svPokemon.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//                searchByName(query.orEmpty())
                mBinding.viewModel?.fetchPokemonData(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        mBinding.scrollUp.setOnClickListener {
            mBinding.rvPokemon.smoothScrollToPosition(0)
        }
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
                if (dy < 0 && mBinding.scrollUp.isShown) {
                    mBinding.scrollUp.hide()
                } else if (dy > 0 && !mBinding.scrollUp.isShown) {
                    mBinding.scrollUp.show()
                }
            }
        })

    }

    private fun loadMoreData() {
        val currentOffset = mBinding.viewModel?.getCurrentOffset() ?: 0
        val nextPageOffset = currentOffset + 20
        mBinding.progressbar.isVisible = true
        mBinding.viewModel?.fetchPokemonList("20", nextPageOffset.toString())
    }


    private fun navigateToDetail(selectedPokemon: Pokemon) {
        val intent = Intent(this@MainActivity, PokemonDetail::class.java)
        intent.putExtra(Constants.EXTRA_ID, selectedPokemon)
        startActivity(intent)
    }

}
