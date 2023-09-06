package com.fa.pokeapp.common.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Other(
    val dream_world: DreamWorld,
    val home: Home,
    @SerializedName("official-artwork") val official_artwork: OfficialArtwork
):Parcelable