package com.fa.pokeapp.common.entities

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    val abilities: List<Ability> = emptyList(),
    val base_experience: Int,
    val forms: List<Form> = emptyList(),
    val game_indices: List<GameIndice> = emptyList(),
    val height: Int,
    val held_items: List<HeldItem> = emptyList(),
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<Move> = emptyList(),
    val name: String,
    val order: Int,
//    val past_types: List<Any> = emptyList(),
    val species: Species,
//    val sprites: Sprites,
    val stats: List<Stat> = emptyList(),
    val types: List<Type> = emptyList(),
    val weight: Int
) : Parcelable