package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.data.repository.SessionRepositoryImpl

class GetUserEmailUseCase(
    private val sessionRepo: SessionRepositoryImpl
) {
    operator fun invoke(): String? = sessionRepo.getEmail()
}