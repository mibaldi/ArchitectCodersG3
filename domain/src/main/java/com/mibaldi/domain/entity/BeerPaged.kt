package com.mibaldi.domain.entity

data class BeerPaged(
    val currentPage: Int,
    val numberOfPages: Int,
    val totalResults: Int,
    val beers: List<Beer>
)