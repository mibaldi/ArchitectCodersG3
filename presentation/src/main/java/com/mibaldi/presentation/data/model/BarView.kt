package com.mibaldi.presentation.data.model

import android.os.Parcelable
import com.mibaldi.domain.entity.Bar
import com.mibaldi.domain.entity.BarLocation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BarView(
    val id: String? = "",
    val name: String,
    val photo: String?,
    val description: String,
    val category: String,
    val price: Int,
    val address: BarLocationView,
    val web: String,
    val phone: String
) : Parcelable

@Parcelize
data class BarLocationView(val lat: Double, val lon: Double, val city: String) : Parcelable

fun Bar.toBarView() = BarView(id, name, photo, description, category, price, address.toBarLocationView(), web, phone)

fun BarLocation.toBarLocationView() = BarLocationView(lat, lon, city)