package com.example.tbcacademy.di.module

import com.example.tbcacademy.data.datasource.FirebaseImageDataSource
import com.example.tbcacademy.data.repository.ImageRepositoryImpl
import com.example.tbcacademy.domain.repository.ImageRepository
import com.example.tbcacademy.domain.usecase.UploadImageUseCase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage =
        FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideImageDataSource(
        storage: FirebaseStorage
    ): FirebaseImageDataSource =
        FirebaseImageDataSource(storage)

    @Provides
    @Singleton
    fun provideImageRepository(
        dataSource: FirebaseImageDataSource
    ): ImageRepository =
        ImageRepositoryImpl(dataSource)

    @Provides
    fun provideUploadImageUseCase(
        repository: ImageRepository
    ): UploadImageUseCase =
        UploadImageUseCase(repository)
}
