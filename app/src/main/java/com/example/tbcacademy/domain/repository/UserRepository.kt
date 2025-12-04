package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.data.common.Resource
import com.example.tbcacademy.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun fetchUsers(): Flow<Resource<Unit>>
    suspend fun observeUsers(): Flow<List<User>>
}