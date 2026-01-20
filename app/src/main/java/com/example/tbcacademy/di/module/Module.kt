package com.example.tbcacademy.di.module

import com.example.tbcacademy.data.remote.ProductsApi
import com.example.tbcacademy.data.repository.FirebaseAuthRepository
import com.example.tbcacademy.data.repository.ProductsRepositoryImpl
import com.example.tbcacademy.domain.repository.AuthRepository
import com.example.tbcacademy.domain.repository.ProductsRepository
import com.example.tbcacademy.domain.usecase.GetCategoriesUseCase
import com.example.tbcacademy.domain.usecase.GetProductsUseCase
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://c37108e6-8faf-4a15-a91c-7d1bc9b48d8a.mock.pstmn.io/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideProductsApi(retrofit: Retrofit): ProductsApi {
        return retrofit.create(ProductsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductsRepository(api: ProductsApi): ProductsRepository {
        return ProductsRepositoryImpl(api)
    }

    @Provides
    fun provideGetCategoriesUseCase(repository: ProductsRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository)
    }

    @Provides
    fun provideGetProductsUseCase(repository: ProductsRepository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object AuthModule {

        @Provides
        fun provideFirebaseAuth(): FirebaseAuth =
            FirebaseAuth.getInstance()

        @Provides
        fun provideAuthRepository(
            impl: FirebaseAuthRepository
        ): AuthRepository = impl
    }
}