package com.example.tbcacademy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tbcacademy.data.paging.UsersPagingSource
import com.example.tbcacademy.data.remote.AuthApi
import com.example.tbcacademy.domain.model.User
import com.example.tbcacademy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    @Named("auth") private val api: AuthApi
) : UserRepository {

    override fun getUsersPaging(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UsersPagingSource(api) }
        ).flow
    }
}