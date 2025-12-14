package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.data.common.ApiResult
import com.example.tbcacademy.domain.model.Location

interface LocationRepository {
    suspend fun getLocations(): ApiResult<List<Location>>
    suspend fun hasLocalData(): Boolean
}
