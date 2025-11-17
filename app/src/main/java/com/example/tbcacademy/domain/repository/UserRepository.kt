package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.User
import com.example.tbcacademy.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(page: Int): Flow<Result<List<User>>>
}