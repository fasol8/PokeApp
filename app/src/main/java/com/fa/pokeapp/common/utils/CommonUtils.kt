package com.fa.pokeapp.common.utils

import com.fa.pokeapp.R

object CommonUtils {

    fun getIdNumber(number: Any) = number.toString().padStart(3, '0')

    fun typeColor(type: String): Int {
        return when (type) {
            "dragon" -> R.color.dragon
            "bug" -> R.color.bug
            "dark" -> R.color.dark
            "ground" -> R.color.earth
            "electric" -> R.color.electric
            "fairy" -> R.color.fairy
            "fighting" -> R.color.fight
            "fire" -> R.color.fire
            "flying" -> R.color.flyer
            "ghost" -> R.color.ghost
            "ice" -> R.color.ice
            "normal" -> R.color.normal
            "grass" -> R.color.plant
            "poison" -> R.color.poison
            "psychic" -> R.color.psychic
            "rock" -> R.color.rock
            "steel" -> R.color.steel
            "water" -> R.color.water
            else -> R.color.main_color
        }
    }

    fun typeSecondColor(type: String): Int {
        return when (type) {
            "dragon" -> R.color.dragon_light
            "bug" -> R.color.bug_light
            "dark" -> R.color.dark_light
            "ground" -> R.color.earth_light
            "electric" -> R.color.electric_light
            "fairy" -> R.color.fairy_light
            "fighting" -> R.color.fight_light
            "fire" -> R.color.fire_light
            "flying" -> R.color.flyer_light
            "ghost" -> R.color.ghost_light
            "ice" -> R.color.ice_light
            "normal" -> R.color.normal_light
            "grass" -> R.color.plant_light
            "poison" -> R.color.poison_light
            "psychic" -> R.color.psychic_light
            "rock" -> R.color.rock_light
            "steel" -> R.color.steel_light
            "water" -> R.color.water_light
            else -> R.color.main_color_light
        }
    }
}