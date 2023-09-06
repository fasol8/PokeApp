package com.fa.pokeapp.common.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenerationIv(
    @SerializedName("diamond-pearl") val diamond_pearl: DiamondPearl,
    @SerializedName("heartgold-soulsilver") val heartgold_soulsilver: HeartgoldSoulsilver,
    val platinum: Platinum
) : Parcelable