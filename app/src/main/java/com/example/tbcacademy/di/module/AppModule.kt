package com.example.tbcacademy.di.module

import com.example.tbcacademy.data.api.CategoryApi
import com.example.tbcacademy.data.api.RetrofitClient
import com.example.tbcacademy.data.repository.CategoryRepositoryImpl
import com.example.tbcacademy.domain.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCategoryApi(): CategoryApi {
        return RetrofitClient.categoryApi
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(api: CategoryApi): CategoryRepository {
        return CategoryRepositoryImpl(api)
    }
}