package com.example.tbcacademy.core.data.repository

import com.example.tbcacademy.core.data.HandleResponse
import com.example.tbcacademy.core.data.mapper.toDomain
import com.example.tbcacademy.core.data.remote.RegisterApi
import com.example.tbcacademy.core.domain.common.Resource
import com.example.tbcacademy.core.domain.model.Field
import com.example.tbcacademy.core.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val apiService: RegisterApi,
    private val handleResponse: HandleResponse
) : RegisterRepository {
    override suspend fun getRegisterFields(): Flow<Resource<List<Field>>> =
        handleResponse.safeApiCall {
            apiService.getRegisterFields()
        }.map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val flattenedFields = resource.data.flatten().map { it.toDomain() }
                    Resource.Success(flattenedFields)
                }
                is Resource.Error -> resource
                is Resource.Loader -> resource
            }
        }
}