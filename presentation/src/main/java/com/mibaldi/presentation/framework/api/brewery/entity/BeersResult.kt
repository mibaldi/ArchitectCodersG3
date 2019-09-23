package com.mibaldi.presentation.framework.api.brewery.entity

import com.google.gson.annotations.SerializedName
import com.mibaldi.domain.entity.Beer
import com.mibaldi.domain.entity.BeerPaged

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
) {

    fun toBeerList(): List<Beer> {
        return data?.map { it.toBeer() } ?: mutableListOf()
    }

    fun toPagedBeer(): BeerPaged {
        return BeerPaged(
            currentPage ?: 0,
            numberOfPages ?: 0,
            totalResults ?: 0,
            data?.map { it.toBeer() } ?: mutableListOf()
        )
    }

}
