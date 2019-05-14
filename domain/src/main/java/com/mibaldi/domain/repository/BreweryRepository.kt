package com.mibaldi.domain.repository

import com.mibaldi.domain.entity.Beer

interface BreweryRepository : BaseRepository {
    suspend fun getBeers(): List<Beer>
}