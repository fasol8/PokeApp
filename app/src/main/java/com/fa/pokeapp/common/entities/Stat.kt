package com.fa.pokeapp.common.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatX
):Parcelable