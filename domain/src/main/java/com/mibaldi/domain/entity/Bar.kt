package com.mibaldi.domain.entity

data class Bar(
    val id: String,
    val name: String,
    val photo: String,
    val description: String,
    val category: String,
    val price: Int,
    val address: BarLocation,
    val web: String,
    val phone: String
) {
    constructor() : this("", "", "", "", "", 0, BarLocation(), "", "")
}

data class BarLocation(val lat: Double, val lon: Double, val city: String) {
    constructor() : this(0.0, 0.0, "")
}