package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.Location

interface LocationRepository {
    suspend fun getLocations(): List<Location>
    suspend fun hasLocalData(): Boolean
}