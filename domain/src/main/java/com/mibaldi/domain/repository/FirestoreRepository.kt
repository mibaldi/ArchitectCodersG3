package com.mibaldi.domain.repository

import arrow.core.Either
import com.mibaldi.domain.entity.Bar
import com.mibaldi.domain.exception.RepositoryException


interface FirestoreRepository : BaseRepository {
    suspend fun updateBar(bar: Bar)
    suspend fun getBarsOnce(): List<Bar>
    suspend fun getBarById(id: String): Either<RepositoryException, Bar>
}