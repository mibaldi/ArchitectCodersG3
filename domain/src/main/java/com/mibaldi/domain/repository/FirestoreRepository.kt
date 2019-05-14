package com.mibaldi.domain.repository

import com.mibaldi.domain.entity.Bar


interface FirestoreRepository : BaseRepository {
    suspend fun addBars(bars: List<Bar>)
    suspend fun getBarsOnce(): List<Bar>
    suspend fun getBarsRealTime()
}