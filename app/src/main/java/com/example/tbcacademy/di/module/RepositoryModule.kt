package com.example.tbcacademy.di.module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.tbcacademy.data.common.HandleFirebaseResponse
import com.example.tbcacademy.data.common.HandleResponse
import com.example.tbcacademy.data.repository.AuthRepositoryImpl
import com.example.tbcacademy.data.repository.FirestoreRepositoryImpl
import com.example.tbcacademy.data.repository.IngredientRepositoryImpl
import com.example.tbcacademy.data.repository.RecipeRepositoryImpl
import com.example.tbcacademy.domain.repository.AuthRepository
import com.example.tbcacademy.domain.repository.FirestoreRepository
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
        impl: IngredientRepositoryImpl,
    ): IngredientRepository

    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
        impl: RecipeRepositoryImpl,
    ): RecipeRepository

    @Binds
    @Singleton
    abstract fun bindFirestoreRepository(
        impl: FirestoreRepositoryImpl,
    ): FirestoreRepository

    companion object {
        @Provides
        @Singleton
        fun provideHandleResponse(): HandleResponse = HandleResponse()

        @Provides
        @Singleton
        fun provideHandleFirebaseResponse(): HandleFirebaseResponse = HandleFirebaseResponse()

        @Provides
        @Singleton
        fun provideAuthRepository(
            auth: FirebaseAuth,
            dataStore: DataStore<Preferences>,
            handleResponse: HandleFirebaseResponse,
        ): AuthRepository = AuthRepositoryImpl(auth, dataStore, handleResponse)
    }
}