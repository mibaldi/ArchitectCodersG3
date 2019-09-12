package com.mibaldi.domain.repository

import com.mibaldi.domain.entity.Beer
import arrow.core.Either
import com.mibaldi.domain.entity.BeerPaged
import com.mibaldi.domain.exception.RepositoryException

interface BreweryRepository : BaseRepository {
    suspend fun getBeersByPage(page: Int): Either<RepositoryException, BeerPaged>

    suspend fun getBeer(id: String): Either<RepositoryException, Beer>
}