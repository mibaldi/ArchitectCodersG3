package com.mibaldi.data.api.brewery

import com.mibaldi.data.api.brewery.entity.BeersResult
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface BreweryApi {

    @GET("beers")
    fun listPopularBeersAsync(
        @Query("key") apiKey: String
    ): Deferred<BeersResult>

}