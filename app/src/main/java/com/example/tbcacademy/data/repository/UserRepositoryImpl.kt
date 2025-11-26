package com.example.tbcacademy.data.repository

import androidx.paging.PagingSource
import com.example.tbcacademy.data.dto.UserDto
import com.example.tbcacademy.data.paging.UsersPagingSource
import com.example.tbcacademy.data.remote.AuthApi
import com.example.tbcacademy.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    @Named("auth") private val api: AuthApi
) : UserRepository {

    override fun getUsersPaging(): PagingSource<Int, UserDto> {
        return UsersPagingSource(api)
    }
}