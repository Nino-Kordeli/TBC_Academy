package com.example.tbcacademy.di.module

import com.example.tbcacademy.data.common.HandleResponse
import com.example.tbcacademy.data.local.dao.UserDao
import com.example.tbcacademy.data.remote.service.UsersService
import com.example.tbcacademy.data.repository.UserRepositoryImpl
import com.example.tbcacademy.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        usersService: UsersService,
        handleResponse: HandleResponse,
        userDao: UserDao
    ): UserRepository {
        return UserRepositoryImpl(
            usersService = usersService,
            handleResponse = handleResponse,
            userDao = userDao
        )
    }
}