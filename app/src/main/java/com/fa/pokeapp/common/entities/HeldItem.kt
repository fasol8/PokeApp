package com.fa.pokeapp.common.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail> = emptyList()
) : Parcelable