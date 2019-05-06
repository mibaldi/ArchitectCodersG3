package com.mibaldi.brewing.data.entities.firestore

data class Bar(val id:String?="",
               val name: String,
               val photo: String?,
               val description: String,
               val category: String,
               val price: Int,
               val address: BarLocation,
               val web: String,
               val phone: String)
data class BarLocation(val lat: Double, val lon: Double, val city: String)

