package com.example.data.di

import com.example.data.repository.food.FoodRepositoryImpl
import com.example.data.repository.recipe.RecipeRepositoryImpl
import com.example.data.repository.step_counter.StepCounterRepositoryImpl
import com.example.data.repository.user_preferences.UserPreferencesRepositoryImpl
import com.example.data.repository.user_session.UserSessionRepositoryImpl
import com.example.data.repository.workout.WorkoutRepositoryImpl
import com.example.data.repository.auth.AuthRepositoryImpl
import com.example.domain.repository.recipe.RecipeRepository
import com.example.domain.repository.step_counter.StepCounterRepository
import com.example.domain.repository.user_preferences.UserPreferencesRepository
import com.example.domain.repository.user_session.UserSessionRepository
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