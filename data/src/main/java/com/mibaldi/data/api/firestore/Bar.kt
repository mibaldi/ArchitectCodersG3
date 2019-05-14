package com.mibaldi.data.api.firestore

import com.mibaldi.domain.entity.Bar
import com.mibaldi.domain.entity.BarLocation

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
