package com.mibaldi.domain.interactors.beer

import com.mibaldi.domain.entity.Beer
import com.mibaldi.domain.interactors.BaseInteractor
import com.mibaldi.domain.repository.BreweryRepository

class BeerInteractor(private val repository: BreweryRepository) : BaseInteractor {
    suspend fun getBeers(): List<Beer> {
        return repository.getBeers()
    }

}