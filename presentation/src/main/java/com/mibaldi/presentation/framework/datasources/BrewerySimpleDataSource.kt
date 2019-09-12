package com.mibaldi.presentation.framework.datasources

import arrow.core.Either
import com.mibaldi.data.datasource.BreweryDataSource
import com.mibaldi.domain.entity.Beer
import com.mibaldi.domain.entity.BeerPaged
import com.mibaldi.domain.exception.RepositoryException
import com.mibaldi.presentation.framework.api.brewery.BreweryApi

class BrewerySimpleDataSource(private val apiClient: BreweryApi) : BreweryDataSource {

    override suspend fun getBeersByPage(page: Int): Either<RepositoryException, BeerPaged> {
        return try {
            val response = apiClient.listPopularBeersAsync(page = page)
            if (response.isSuccessful) {
                response.body()?.let {
                    Either.Right(it.toPagedBeer())
                } ?: Either.Left(RepositoryException.DataNotFoundException)
            } else {
                Either.Left(RepositoryException.DataNotFoundException)
            }
        } catch (e: Throwable) {
            Either.Left(RepositoryException.DataNotFoundException)
        }

    }

    override suspend fun getBeer(id: String): Either<RepositoryException, Beer> {
        return try {
            val response = apiClient.getBeer(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Either.Right(it.toBeer())
                } ?: Either.Left(RepositoryException.DataNotFoundException)
            } else {
                Either.Left(RepositoryException.DataNotFoundException)
            }
        } catch (e: Throwable) {
            Either.Left(RepositoryException.DataNotFoundException)
        }
    }

}