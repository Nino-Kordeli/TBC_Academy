package com.example.tbcacademy.domain.repository

import com.example.tbcacademy.domain.model.Result
import com.example.tbcacademy.domain.model.User

interface UserRepository {
    suspend fun getUsers(page: Int = 1): Result<List<User>>
    suspend fun getUser(): User
}