package com.mibaldi.domain.repository

import com.mibaldi.domain.entity.Bar


interface FirestoreRepository : BaseRepository {
    suspend fun updateBar(bar: Bar)
    suspend fun getBarsOnce(): List<Bar>
}