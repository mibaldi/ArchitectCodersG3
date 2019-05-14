package com.mibaldi.data.repository

import com.mibaldi.data.datasource.FirestoreDataSource
import com.mibaldi.domain.entity.Bar
import com.mibaldi.domain.repository.FirestoreRepository


class FirestoreSimpleRepository(
    private val apiSource: FirestoreDataSource
) : FirestoreRepository {
    override suspend fun addBars(bars: List<Bar>) {
        apiSource.addBars(bars)
    }

    override suspend fun getBarsOnce(): List<Bar> {
        return apiSource.getBarsOnce()
    }
    override suspend fun getBarsRealTime() {
        apiSource.getBarsRealTime()
    }
}