package com.fa.pokeapp.common.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RedBlue(
    val back_default: String,
    val back_gray: String,
    val back_transparent: String,
    val front_default: String,
    val front_gray: String,
    val front_transparent: String
):Parcelable