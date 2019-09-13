package com.mibaldi.data.repository

import arrow.core.Either
import com.mibaldi.data.datasource.FirestoreDataSource
import com.mibaldi.domain.entity.Bar
import com.mibaldi.domain.exception.RepositoryException
import com.mibaldi.domain.repository.FirestoreRepository


class FirestoreSimpleRepository(
    private val apiSource: FirestoreDataSource
) : FirestoreRepository {
    override suspend fun getBarById(id: String): Either<RepositoryException, Bar> {
        return apiSource.getBarById(id)
    }

    override suspend fun updateBar(bar: Bar) {
        apiSource.updateBar(bar)
    }

    override suspend fun getBarsOnce(): List<Bar> {
        return apiSource.getBarsOnce()
    }

}