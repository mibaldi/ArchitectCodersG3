package com.mibaldi.domain.interactors.beer

import com.mibaldi.domain.entity.Beer
import com.mibaldi.domain.interactors.BaseInteractor

interface BeerInteractor : BaseInteractor {
    suspend fun getBeers(): List<Beer>

}