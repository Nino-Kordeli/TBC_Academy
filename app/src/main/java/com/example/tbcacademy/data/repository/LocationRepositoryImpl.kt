package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.common.ApiResult
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

    override suspend fun getLocations(): ApiResult<List<Location>> {
        return try {
            val locationsFromApi = api.getLocations()

            dao.deleteAll()
            dao.insertLocations(locationsFromApi.dtoListToEntities())

            ApiResult.Success(locationsFromApi.dtoToDomain())
        } catch (e: Exception) {
            val localData = dao.getAllLocations()

            if (localData.isNotEmpty()) {
                ApiResult.Success(localData.entityToDomain())
            } else {
                ApiResult.Error(e)
            }
        }
    }


    override suspend fun hasLocalData(): Boolean {
        return dao.getCount() > 0
    }
}