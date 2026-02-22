package com.example.data.di

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.FoodRepositoryImpl
import com.example.data.repository.StepCounterRepositoryImpl
import com.example.data.repository.UserPreferencesRepositoryImpl
import com.example.data.repository.UserSessionRepositoryImpl
import com.example.domain.repository.StepCounterRepository
import com.example.domain.repository.UserPreferencesRepository
import com.example.domain.repository.UserSessionRepository
import com.example.domain.repository.auth.AuthRepository
import com.example.domain.repository.food.FoodRepository
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
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

//    @Singleton
//    abstract fun bindDiaryRepository(
//        impl: DiaryRepositoryImpl
//    ): DiaryRepository

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        impl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository

    @Binds
    @Singleton
    abstract fun bindUserSessionRepository(
        impl: UserSessionRepositoryImpl
    ): UserSessionRepository

    @Binds
    @Singleton
    abstract fun bindStepCounterRepository(
        impl: StepCounterRepositoryImpl
    ): StepCounterRepository
}