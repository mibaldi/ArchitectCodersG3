package com.mibaldi.data.api.brewery.entity

import com.google.gson.annotations.SerializedName

data class BeersResult(
    @SerializedName("currentPage")
    var currentPage: Int? = null,
    @SerializedName("numberOfPages")
    var numberOfPages: Int? = null,
    @SerializedName("totalResults")
    var totalResults: Int? = null,
    @SerializedName("data")
    var data: List<Datum>? = null,
    @SerializedName("status")
    var status: String? = null
)
