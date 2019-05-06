package com.mibaldi.brewing.interactors

import androidx.appcompat.app.AppCompatActivity
import com.mibaldi.brewing.R
import com.mibaldi.brewing.data.model.Beer
import com.mibaldi.brewing.data.model.toBeerList
import com.mibaldi.brewing.repositories.BreweryDB
import com.mibaldi.brewing.repositories.RegionRepository

class BeerInteractor(activity: AppCompatActivity) {


    private val apiKey = activity.getString(R.string.api_key)
    private val regionRepository = RegionRepository(activity)

    suspend fun getBeers() : List<Beer> {
        val beersResult = BreweryDB.service
            .listPopularMoviesAsync(
                apiKey
            )
            .await()
        return beersResult.toBeerList()
    }

}