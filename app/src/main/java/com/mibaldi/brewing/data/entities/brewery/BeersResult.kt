package com.mibaldi.brewing.data.entities.brewery

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BeersResult {

    @SerializedName("currentPage")
    @Expose
    var currentPage: Int? = null
    @SerializedName("numberOfPages")
    @Expose
    var numberOfPages: Int? = null
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null
    @SerializedName("status")
    @Expose
    var status: String? = null

}
