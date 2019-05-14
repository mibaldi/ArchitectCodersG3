package com.mibaldi.data.datasource

import com.mibaldi.domain.entity.Bar


interface FirestoreDataSource : BaseDataSource {
    suspend fun addBars(bars: List<Bar>)

    suspend fun getBarsOnce(): List<Bar>
    suspend fun getBarsRealTime()
}