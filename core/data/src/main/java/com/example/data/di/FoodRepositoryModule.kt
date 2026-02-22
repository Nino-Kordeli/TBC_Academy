package com.example.data.di

import com.example.data.repository.FoodRepositoryImpl
import com.example.domain.repository.food.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FoodRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFoodRepository(
        impl: FoodRepositoryImpl
    ): FoodRepository
}