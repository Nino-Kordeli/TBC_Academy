package com.example.tbcacademy.di.module

import com.example.tbcacademy.data.remote.RegisterApi
import com.example.tbcacademy.data.repository.RegisterRepositoryImpl
import com.example.tbcacademy.domain.repository.RegisterRepository
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
    fun provideRepo(api: RegisterApi): RegisterRepository =
        RegisterRepositoryImpl(api)
}
