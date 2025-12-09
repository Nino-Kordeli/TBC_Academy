package com.example.tbcacademy.di.module

import com.example.tbcacademy.domain.repository.AuthRepository
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
    fun provideAuthRepository(
        auth: com.google.firebase.auth.FirebaseAuth,
        dataStore: androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences>
    ): AuthRepository {
        return com.example.tbcacademy.data.repository.AuthRepositoryImpl(auth, dataStore)
    }
}
