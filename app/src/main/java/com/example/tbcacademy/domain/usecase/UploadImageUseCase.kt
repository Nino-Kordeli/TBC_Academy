package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.repository.ImageRepository

class UploadImageUseCase(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(bytes: ByteArray): String {
        return repository.uploadImage(bytes)
    }
}