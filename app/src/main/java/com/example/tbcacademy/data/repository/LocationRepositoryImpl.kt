package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.common.ApiResult
import com.example.tbcacademy.data.common.safeApiCall
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
        return when (val result = safeApiCall { api.getLocations() }) {

            is ApiResult.Success -> {
                dao.deleteAll()
                dao.insertLocations(result.data.dtoListToEntities())

                ApiResult.Success(result.data.dtoToDomain())
            }

            is ApiResult.Error -> {
                val cached = dao.getAllLocations()

                if (cached.isNotEmpty()) {
                    ApiResult.Success(cached.entityToDomain())
                } else {
                    ApiResult.Error(result.throwable)
                }
            }
        }
    }


    override suspend fun hasLocalData(): Boolean {
        return dao.getCount() > 0
    }
}