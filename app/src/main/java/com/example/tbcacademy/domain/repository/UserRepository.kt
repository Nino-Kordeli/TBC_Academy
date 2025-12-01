package com.example.tbcacademy.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.tbcacademy.data.dto.UserDto
import com.example.tbcacademy.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsersPaging(): PagingSource<Int, User>
}
