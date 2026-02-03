package com.example.tbcacademy.core.domain.repository

import com.example.tbcacademy.core.domain.common.Resource
import com.example.tbcacademy.core.domain.model.Field
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun getRegisterFields(): Flow<Resource<List<Field>>>
}