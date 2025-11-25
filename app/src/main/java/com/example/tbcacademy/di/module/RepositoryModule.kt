package com.example.tbcacademy.di.module

import com.example.tbcacademy.data.repository.MessageRepositoryImpl
import com.example.tbcacademy.domain.repository.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMessageRepository(
        impl: MessageRepositoryImpl
    ): MessageRepository
}
