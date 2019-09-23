package com.mibaldi.domain.interactors.bar

import arrow.core.Either
import com.mibaldi.domain.entity.Bar
import com.mibaldi.domain.exception.RepositoryException
import com.mibaldi.domain.interactors.BaseInteractor
import com.mibaldi.domain.repository.FirestoreRepository

class GetBarInteractor(private val repository: FirestoreRepository) : BaseInteractor {
    suspend fun getAllBars(): List<Bar> {
        return repository.getBarsOnce()
    }

    suspend fun getBar(id: String): Either<RepositoryException, Bar> {
        return repository.getBarById(id)
    }
}