package com.example.tbcacademy.domain.repository

import androidx.paging.PagingData
import com.example.tbcacademy.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsersPaging(): Flow<PagingData<User>>
}