package com.mibaldi.presentation.framework.api.brewery

import com.mibaldi.presentation.BuildConfig
import com.mibaldi.presentation.framework.api.brewery.entity.BeersResult
import com.mibaldi.presentation.framework.api.brewery.entity.Datum
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BreweryApi {
    companion object {
        const val API_KEY = BuildConfig.KEY_BREWERY

    }

    @GET("beers")
    suspend fun listPopularBeersAsync(
        @Query("key") apiKey: String = API_KEY,
        @Query("p") page: Int,
        @Query("hasLabels") hasLabels: String = "Y"
    ): Response<BeersResult>

    @GET("beer/{beerId}")
    suspend fun getBeer(
        @Path("beerId") beerId: String,
        @Query("key") apiKey: String = API_KEY
    ): Response<Datum>

}