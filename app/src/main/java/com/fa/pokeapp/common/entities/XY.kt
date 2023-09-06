package com.fa.pokeapp.common.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class XY(
    val front_default: String,
    val front_female: String,
    val front_shiny: String,
    val front_shiny_female: String
):Parcelable