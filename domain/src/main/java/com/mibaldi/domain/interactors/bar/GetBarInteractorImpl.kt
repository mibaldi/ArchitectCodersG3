package com.mibaldi.domain.interactors.bar

import com.mibaldi.domain.entity.Bar
import com.mibaldi.domain.repository.FirestoreRepository

class GetBarSimpleInteractor(private val repository: FirestoreRepository) : GetBarInteractor {

    override suspend fun getAllBars(): List<Bar> {
        return repository.getBarsOnce()
    }


}