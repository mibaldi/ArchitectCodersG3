package com.mibaldi.presentation.framework.api.firestore

data class BarResponse(
    var id: String? = "",
    var name: String = "",
    var photo: String? = "",
    var description: String = "",
    var category: String = "",
    var price: Int = 0,
    var address: BarLocationResponse = BarLocationResponse(),
    var web: String = "",
    var phone: String = ""
)

data class BarLocationResponse(var lat: Double = 0.0, var lon: Double = 0.0, var city: String = "")
