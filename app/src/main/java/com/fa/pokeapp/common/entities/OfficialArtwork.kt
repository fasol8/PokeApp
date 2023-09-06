package com.fa.pokeapp.common.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfficialArtwork(
    val front_default: String,
    val front_shiny: String
) : Parcelable