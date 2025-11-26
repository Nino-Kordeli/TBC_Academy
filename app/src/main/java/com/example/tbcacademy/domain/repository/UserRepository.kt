package com.example.tbcacademy.domain.repository

import androidx.paging.PagingSource
import com.example.tbcacademy.data.dto.UserDto

interface UserRepository {
    fun getUsersPaging(): PagingSource<Int, UserDto>
}