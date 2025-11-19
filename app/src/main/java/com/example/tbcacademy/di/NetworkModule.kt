package com.example.tbcacademy.di

import com.example.tbcacademy.data.remote.AuthApi
import com.example.tbcacademy.data.remote.RetrofitInstance
import com.example.tbcacademy.domain.repository.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("auth")
    fun provideAuthApi(session: SessionRepository): AuthApi =
        RetrofitInstance.createService { session.getTokenSync() }

    @Provides
    @Singleton
    @Named("noAuth")
    fun provideAuthApiWithoutAuth(): AuthApi =
        RetrofitInstance.createServiceWithoutAuth()
}
