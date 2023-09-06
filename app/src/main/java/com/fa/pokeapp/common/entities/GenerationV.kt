package com.fa.pokeapp.common.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenerationV(
    @SerializedName("black-white") val black_white: BlackWhite
):Parcelable