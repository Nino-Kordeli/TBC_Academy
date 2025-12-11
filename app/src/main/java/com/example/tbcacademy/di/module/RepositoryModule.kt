package com.example.tbcacademy.di.module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.tbcacademy.data.common.SafeCall
import com.example.tbcacademy.data.repository.AuthRepositoryImpl
import com.example.tbcacademy.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
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
    fun provideSafeCall(): SafeCall {
        return SafeCall()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        dataStore: DataStore<Preferences>,
        safeCall: SafeCall
    ): AuthRepository {
        return AuthRepositoryImpl(
            auth = auth,
            dataStore = dataStore,
            safeCall = safeCall
        )
    }
}