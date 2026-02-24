package com.example.data.di

import com.example.data.repository.FoodRepositoryImpl
import com.example.data.repository.RecipeRepositoryImpl
import com.example.data.repository.StepCounterRepositoryImpl
import com.example.data.repository.UserPreferencesRepositoryImpl
import com.example.data.repository.UserSessionRepositoryImpl
import com.example.data.repository.WorkoutRepositoryImpl
import com.example.data.repository.auth.AuthRepositoryImpl
import com.example.domain.RecipeRepository
import com.example.domain.repository.StepCounterRepository
import com.example.domain.repository.UserPreferencesRepository
import com.example.domain.repository.UserSessionRepository
import com.example.domain.repository.workout.WorkoutRepository
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

    @Binds
    @Singleton
    abstract fun bindFoodRepository(
        impl: FoodRepositoryImpl
    ): FoodRepository

    @Binds
    @Singleton
    abstract fun bindWorkoutRepository(
        impl: WorkoutRepositoryImpl
    ): WorkoutRepository

    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
        impl: RecipeRepositoryImpl
    ): RecipeRepository
}