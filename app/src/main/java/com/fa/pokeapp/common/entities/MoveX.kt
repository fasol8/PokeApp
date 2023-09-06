package com.fa.pokeapp.common.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoveX(
    val name: String,
    val url: String
):Parcelable