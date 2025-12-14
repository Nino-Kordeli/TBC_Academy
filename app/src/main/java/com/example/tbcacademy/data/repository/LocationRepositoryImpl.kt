package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.local.dao.LocationDao
import com.example.tbcacademy.data.mapper.dtoListToEntities
import com.example.tbcacademy.data.mapper.dtoToDomain
import com.example.tbcacademy.data.mapper.entityToDomain
import com.example.tbcacademy.data.remote.service.LocationApi
import com.example.tbcacademy.domain.model.Location
import com.example.tbcacademy.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val api: LocationApi,
    private val dao: LocationDao,
) : LocationRepository {

    override suspend fun getLocations(): List<Location> {
        return try {
            val locationsFromApi = api.getLocations()

            dao.deleteAll()
            dao.insertLocations(locationsFromApi.dtoListToEntities())

            locationsFromApi.dtoToDomain()
        } catch (e: Exception) {
            val locationsFromDb = dao.getAllLocations()
            locationsFromDb.entityToDomain()
        }
    }

    override suspend fun hasLocalData(): Boolean {
        return dao.getCount() > 0
    }
}