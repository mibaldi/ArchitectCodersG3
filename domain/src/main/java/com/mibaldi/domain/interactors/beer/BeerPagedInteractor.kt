package com.mibaldi.domain.interactors.beer

import arrow.core.Either
import com.mibaldi.domain.entity.BeerPaged
import com.mibaldi.domain.exception.RepositoryException
import com.mibaldi.domain.interactors.BaseInteractor
import com.mibaldi.domain.repository.BreweryRepository

class BeerPagedInteractor(private val repository: BreweryRepository) : BaseInteractor {
    suspend fun getBeersByPage(page: Int): Either<RepositoryException, BeerPaged> {
        return repository.getBeersByPage(page)
    }

}