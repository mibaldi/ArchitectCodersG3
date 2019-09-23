package com.mibaldi.data.datasource


interface LocationDataSource {
    suspend fun findLastRegion(): String?
}