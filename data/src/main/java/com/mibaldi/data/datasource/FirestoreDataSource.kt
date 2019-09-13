package com.mibaldi.data.datasource

import arrow.core.Either
import com.mibaldi.domain.entity.Bar
import com.mibaldi.domain.exception.RepositoryException


interface FirestoreDataSource : BaseDataSource {
    suspend fun getBarsOnce(): List<Bar>
    suspend fun updateBar(bar: Bar)
    suspend fun getBarById(id: String): Either<RepositoryException, Bar>
}