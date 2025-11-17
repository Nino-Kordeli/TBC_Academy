package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.data.repository.SessionRepositoryImpl

class GetUserEmailUseCase(
    private val sessionRepo: SessionRepositoryImpl
) {
    suspend operator fun invoke(): String? = sessionRepo.readEmail()
}