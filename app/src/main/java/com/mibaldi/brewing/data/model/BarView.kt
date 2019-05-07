package com.mibaldi.brewing.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BarView(val id:String?="",
               val name: String,
               val photo: String?,
               val description: String,
               val category: String,
               val price: Int,
               val address: BarLocationView,
               val web: String,
               val phone: String):Parcelable

@Parcelize
data class BarLocationView(val lat: Double, val lon: Double, val city: String): Parcelable

