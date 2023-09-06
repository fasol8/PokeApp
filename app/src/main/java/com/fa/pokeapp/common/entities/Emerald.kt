package com.fa.pokeapp.common.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Emerald(
    val front_default: String,
    val front_shiny: String
) : Parcelable