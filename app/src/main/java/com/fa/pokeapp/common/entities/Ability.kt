package com.fa.pokeapp.common.entities

 import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
):Parcelable