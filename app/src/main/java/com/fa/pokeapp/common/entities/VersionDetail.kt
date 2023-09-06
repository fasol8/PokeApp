package com.fa.pokeapp.common.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VersionDetail(
    val rarity: Int,
    val version: Version
):Parcelable