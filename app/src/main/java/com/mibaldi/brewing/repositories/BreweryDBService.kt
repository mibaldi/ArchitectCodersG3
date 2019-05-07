package com.mibaldi.brewing.repositories

import com.mibaldi.brewing.data.entities.brewery.BeersResult
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface BreweryDBService  {

    @GET("beers")
    fun listPopularBeersAsync(
        @Query("key") apiKey: String): Deferred<BeersResult>
}