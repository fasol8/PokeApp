package com.fa.pokeapp.common.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenerationVi(
    @SerializedName("omegaruby-alphasapphire") val omegaruby_alphasapphire: OmegarubyAlphasapphire,
    @SerializedName("x-y") val x_y: XY
) : Parcelable