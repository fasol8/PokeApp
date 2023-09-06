package com.fa.pokeapp.common.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenerationIi(
    val crystal: Crystal,
    val gold: Gold,
    val silver: Silver
):Parcelable