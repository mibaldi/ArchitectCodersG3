package com.mibaldi.presentation.framework.api.brewery

import com.mibaldi.presentation.framework.api.brewery.entity.BeersResult
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface BreweryApi {

    @GET("beers")
    fun listPopularBeersAsync(
        @Query("key") apiKey: String
    ): Deferred<BeersResult>

}