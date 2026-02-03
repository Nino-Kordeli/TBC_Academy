package com.example.tbcacademy.core.data.di.module

import com.example.tbcacademy.core.data.HandleResponse
import com.example.tbcacademy.core.data.remote.RegisterApi
import com.example.tbcacademy.core.data.repository.RegisterRepositoryImpl
import com.example.tbcacademy.core.domain.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RegisterModule {

    @Provides
    fun provideApi(retrofit: Retrofit): RegisterApi =
        retrofit.create(RegisterApi::class.java)

    @Provides
    fun provideRepo(
        api: RegisterApi,
        handleResponse: HandleResponse
    ): RegisterRepository =
        RegisterRepositoryImpl(api, handleResponse)
}
