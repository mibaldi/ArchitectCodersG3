package com.mibaldi.brewing.data.entities.firestore

import com.mibaldi.brewing.data.model.BarLocationView
import com.mibaldi.brewing.data.model.BarView

data class Bar(var id:String?="",
               var name: String = "",
               var photo: String?= "",
               var description: String= "",
               var category: String= "",
               var price: Int= 0,
               var address: BarLocation= BarLocation(),
               var web: String= "",
               var phone: String= "")
data class BarLocation(var lat: Double = 0.0, var lon: Double=0.0, var city: String = "")


fun Bar.toBarView() = BarView(id,name,photo,description,category,price,address.toBarLocationView(),web,phone)
fun BarLocation.toBarLocationView() = BarLocationView(lat,lon,city)


