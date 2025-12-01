package com.example.tbcacademy.di.module

import com.example.tbcacademy.data.repository.AuthRepositoryImpl
import com.example.tbcacademy.data.repository.SessionRepositoryImpl
import com.example.tbcacademy.data.repository.UserRepositoryImpl
import com.example.tbcacademy.domain.repository.AuthRepository
import com.example.tbcacademy.domain.repository.SessionRepository
import com.example.tbcacademy.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindModule {

    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindSessionRepository(impl: SessionRepositoryImpl): SessionRepository
}