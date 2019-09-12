package com.mibaldi.presentation.data.model

import android.os.Parcelable
import com.mibaldi.domain.entity.Beer
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BeerView(
    val id: String,
    val title: String,
    val image: String,
    var observation: String?,
    var rating: Float = 0f
) : Parcelable

fun Beer.toBeerView() = BeerView(id, title, image, observation, rating)

fun BeerView.toBeer() = Beer(id, title, image, observation, rating)