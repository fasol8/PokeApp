package com.fa.pokeapp.mainModule.view

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.fa.pokeapp.R
import com.fa.pokeapp.common.Utils.CommonUtils
import com.fa.pokeapp.common.Utils.Constants
import com.fa.pokeapp.common.entities.Pokemon
import com.fa.pokeapp.common.entities.Stat
import com.fa.pokeapp.common.entities.Type
import com.fa.pokeapp.databinding.ActivityPokemonDetailBinding
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class PokemonDetail : AppCompatActivity() {

    private lateinit var mBinding: ActivityPokemonDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val selectedPokemon: Pokemon? = intent.getParcelableExtra(Constants.EXTRA_ID)
        if (selectedPokemon != null) {
            getUI(selectedPokemon)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getUI(pokeDetail: Pokemon) {
        val url =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokeDetail.id}.png"

        Picasso.get().load(url).into(mBinding.ivPokeDetail)
        mBinding.tvPokeName.text = pokeDetail.name
        mBinding.tvPokeId.text = "#" + CommonUtils.getIdNumber(pokeDetail.id)
        mBinding.tvWeight.text = "Weight: ${pokeDetail.weight.toString()}"
        mBinding.tvHeight.text = "Height: ${pokeDetail.height.toString()}"
        prepareTypes(pokeDetail.types)
        prepareStats(pokeDetail.stats)
    }

    private fun prepareTypes(types: List<Type>) {
        if (types.size <= 1) {
            mBinding.tvType1.text = types[0].type.name
            mBinding.tvType1.setBackgroundResource(CommonUtils.typeColor(types[0].type.name))
        } else {
            mBinding.tvType1.text = types[0].type.name
            mBinding.tvType2.text = types[1].type.name
            mBinding.tvType1.setBackgroundResource(CommonUtils.typeColor(types[0].type.name))
            mBinding.tvType2.setBackgroundResource(CommonUtils.typeColor(types[1].type.name))
        }

        val firstColor = CommonUtils.typeColor(types[0].type.name)
        val secondColor = CommonUtils.typeSecondColor(types[0].type.name)
        mBinding.tvPokeName.setTextColor(getColor(firstColor))
        updateCardViewGradientColors(getColor(secondColor), getColor(firstColor))
//        mBinding.cardView.setCardBackgroundColor(getColor(firstColor))
    }

    private fun prepareStats(stats: List<Stat>) {
        updateHeight(mBinding.viewHp, stats[0].base_stat)
        updateHeight(mBinding.viewAttack, stats[1].base_stat)
        updateHeight(mBinding.viewDefense, stats[2].base_stat)
        updateHeight(mBinding.viewSpecialAttack, stats[3].base_stat)
        updateHeight(mBinding.viewSpecialDefense, stats[4].base_stat)
        updateHeight(mBinding.viewSpeed, stats[5].base_stat)
        mBinding.viewHp.setBackgroundResource(prepareStatsColor(stats[0].base_stat))
        mBinding.viewAttack.setBackgroundResource(prepareStatsColor(stats[1].base_stat))
        mBinding.viewDefense.setBackgroundResource(prepareStatsColor(stats[2].base_stat))
        mBinding.viewSpecialAttack.setBackgroundResource(prepareStatsColor(stats[3].base_stat))
        mBinding.viewSpecialDefense.setBackgroundResource(prepareStatsColor(stats[4].base_stat))
        mBinding.viewSpeed.setBackgroundResource(prepareStatsColor(stats[5].base_stat))
        mBinding.tvHP.text = stats[0].base_stat.toString()
        mBinding.tvAttack.text = stats[1].base_stat.toString()
        mBinding.tvDefense.text = stats[2].base_stat.toString()
        mBinding.tvAttackSpecial.text = stats[3].base_stat.toString()
        mBinding.tvDefenseSpecial.text = stats[4].base_stat.toString()
        mBinding.tvSpeed.text = stats[5].base_stat.toString()
    }

    private fun prepareStatsColor(baseStat: Int): Int {
        if (baseStat < 75) {
            return R.color.md_theme_light_surfaceTint
        } else {
            return R.color.stats_god
        }
    }

    private fun updateCardViewGradientColors(startColor: Int, endColor: Int) {
        val gradientDrawable =
            ContextCompat.getDrawable(
                this,
                R.drawable.vertical_gradient_background
            ) as GradientDrawable
        gradientDrawable.colors = intArrayOf(startColor, endColor)
        mBinding.cardView.background = gradientDrawable
    }

    private fun updateHeight(view: View, baseStat: Int) {
        val params = view.layoutParams
        params.height = pxToDp(baseStat.toFloat())
    }

    private fun pxToDp(px: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics)
            .roundToInt()
    }

}