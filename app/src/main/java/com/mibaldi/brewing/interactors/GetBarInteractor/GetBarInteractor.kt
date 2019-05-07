package com.mibaldi.brewing.interactors.GetBarInteractor

import com.mibaldi.brewing.data.model.BarView

interface GetBarInteractor {
    suspend fun getAllBars() : List<BarView>
}