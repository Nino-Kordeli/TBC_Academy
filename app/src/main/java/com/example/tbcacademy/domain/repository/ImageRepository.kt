package com.example.tbcacademy.domain.repository

interface ImageRepository {
    suspend fun uploadImage(bytes: ByteArray): String
}