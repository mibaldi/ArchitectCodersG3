package com.mibaldi.domain.interactors.bar

import com.mibaldi.domain.entity.Bar
import com.mibaldi.domain.interactors.BaseInteractor
import com.mibaldi.domain.repository.FirestoreRepository

class UpdateBarInteractor(private val repository: FirestoreRepository) : BaseInteractor {
    suspend fun updateBar(bar: Bar) {
        return repository.updateBar(bar)
    }
}