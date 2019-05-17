package com.mibaldi.presentation.data.model

import android.os.Parcelable
import com.mibaldi.data.api.brewery.entity.BeersResult
import com.mibaldi.data.api.brewery.entity.Datum
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Beer(

    val id: String,
    val title: String
) : Parcelable

fun BeersResult.toBeerList(): List<Beer> {
    return data?.map { it.toBeer() } ?: mutableListOf()
}

fun Datum.toBeer(): Beer {
    return Beer(id ?: "", nameDisplay ?: "")
}