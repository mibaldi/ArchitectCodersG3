package com.mibaldi.domain.interactors.bar

import com.mibaldi.domain.entity.Bar
import com.mibaldi.domain.interactors.BaseInteractor

interface GetBarInteractor : BaseInteractor {
    suspend fun getAllBars(): List<Bar>
}