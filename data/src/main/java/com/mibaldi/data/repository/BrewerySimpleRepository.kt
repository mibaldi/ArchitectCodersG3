package com.mibaldi.data.repository

import arrow.core.Either
import com.mibaldi.data.datasource.BreweryDataSource
import com.mibaldi.domain.entity.Beer
import com.mibaldi.domain.entity.BeerPaged
import com.mibaldi.domain.exception.RepositoryException
import com.mibaldi.domain.repository.BreweryRepository


class BrewerySimpleRepository(
    private val apiSource: BreweryDataSource
) : BreweryRepository {
    override suspend fun getBeersByPage(page: Int): Either<RepositoryException, BeerPaged> {
        return apiSource.getBeersByPage(page)
    }

    override suspend fun getBeer(id: String): Either<RepositoryException, Beer> {
        return apiSource.getBeer(id)
    }

}