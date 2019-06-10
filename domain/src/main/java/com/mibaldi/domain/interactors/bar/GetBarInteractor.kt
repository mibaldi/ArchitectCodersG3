package com.mibaldi.domain.interactors.bar

import com.mibaldi.domain.entity.Bar
import com.mibaldi.domain.interactors.BaseInteractor
import com.mibaldi.domain.repository.FirestoreRepository

class GetBarInteractor(private val repository: FirestoreRepository) : BaseInteractor {
    suspend fun getAllBars(): List<Bar> {
        return repository.getBarsOnce()
    }
}