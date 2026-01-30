package com.example.tbcacademy.di.module

import com.example.tbcacademy.data.remote.ApiService
import com.example.tbcacademy.data.repository.PostRepositoryImpl
import com.example.tbcacademy.data.repository.StoryRepositoryImpl
import com.example.tbcacademy.domain.repository.PostRepository
import com.example.tbcacademy.domain.repository.StoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideStoryRepository(api: ApiService): StoryRepository =
        StoryRepositoryImpl(api)

    @Provides
    @Singleton
    fun providePostRepository(api: ApiService): PostRepository =
        PostRepositoryImpl(api)
}