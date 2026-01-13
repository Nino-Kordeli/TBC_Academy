package com.example.tbcacademy.data.repository

import com.example.tbcacademy.data.datasource.FirebaseImageDataSource
import com.example.tbcacademy.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val dataSource: FirebaseImageDataSource
) : ImageRepository {

    override suspend fun uploadImage(bytes: ByteArray): String {
        return dataSource.uploadImage(bytes)
    }
}