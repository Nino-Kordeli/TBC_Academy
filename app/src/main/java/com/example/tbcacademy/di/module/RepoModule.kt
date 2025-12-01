package com.example.tbcacademy.di.module

import com.example.tbcacademy.data.repository.ProfileRepositoryImpl
import com.example.tbcacademy.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindProfileRepo(impl: ProfileRepositoryImpl): ProfileRepository
}