package com.example.tbcacademy.di.module

import com.example.tbcacademy.data.remote.ApiService
import com.example.tbcacademy.data.repository.CardsRepositoryImpl
import com.example.tbcacademy.domain.repository.CardsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://mocki.io/v1/"

    @Singleton
    @Provides
    fun provideApi(): ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideRepository(api: ApiService): CardsRepository = CardsRepositoryImpl(api)
}
