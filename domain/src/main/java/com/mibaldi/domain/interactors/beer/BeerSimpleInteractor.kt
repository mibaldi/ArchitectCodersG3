package com.mibaldi.domain.interactors.beer

import com.mibaldi.domain.entity.Beer
import com.mibaldi.domain.repository.BreweryRepository

class BeerSimpleInteractor constructor(private val repository: BreweryRepository) : BeerInteractor {

    override suspend fun getBeers(): List<Beer> {
        return repository.getBeers()
    }

}