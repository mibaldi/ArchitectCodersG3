package com.mibaldi.data.datasource

import arrow.core.Either
import com.mibaldi.domain.entity.Beer
import com.mibaldi.domain.entity.BeerPaged
import com.mibaldi.domain.exception.RepositoryException


interface BreweryDataSource : BaseDataSource {
    suspend fun getBeersByPage(page: Int = 1): Either<RepositoryException, BeerPaged>
    suspend fun getBeer(id: String): Either<RepositoryException, Beer>
}