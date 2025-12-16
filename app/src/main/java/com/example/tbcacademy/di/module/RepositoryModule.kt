package com.example.tbcacademy.di.module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.tbcacademy.data.common.SafeCall
import com.example.tbcacademy.data.repository.AuthRepositoryImpl
import com.example.tbcacademy.data.repository.IngredientRepositoryImpl
import com.example.tbcacademy.data.repository.RecipeRepositoryImpl
import com.example.tbcacademy.domain.repository.AuthRepository
import com.example.tbcacademy.domain.repository.IngredientRepository
import com.example.tbcacademy.domain.repository.RecipeRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindIngredientRepository(
        impl: IngredientRepositoryImpl
    ): IngredientRepository

    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
        impl: RecipeRepositoryImpl
    ): RecipeRepository

    companion object {
        @Provides
        @Singleton
        fun provideSafeCall(): SafeCall = SafeCall()

        @Provides
        @Singleton
        fun provideAuthRepository(
            auth: FirebaseAuth,
            dataStore: DataStore<Preferences>,
            safeCall: SafeCall,
        ): AuthRepository = AuthRepositoryImpl(auth, dataStore, safeCall)
    }
}