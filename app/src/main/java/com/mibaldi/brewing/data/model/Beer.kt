package com.mibaldi.brewing.data.model

import android.os.Parcelable
import com.mibaldi.brewing.data.entities.brewery.BeersResult
import com.mibaldi.brewing.data.entities.brewery.Datum
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Beer(

    val id: String,
    val title: String
) : Parcelable

fun BeersResult.toBeerList(): List<Beer>{
   return data?.map{it.toBeer()} ?: mutableListOf<Beer>()
}
fun Datum.toBeer(): Beer{
    return Beer(id?:"",nameDisplay?:"")
}