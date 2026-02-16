package com.example.data.di

import com.example.data.repository.FoodRepositoryImpl
import com.example.domain.repository.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFoodRepository(
        impl: FoodRepositoryImpl
    ): FoodRepository

    /*@Binds
    @Singleton
    abstract fun bindDiaryRepository(
        impl: DiaryRepositoryImpl
    ): DiaryRepository*/
}