package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.data.repository.SessionRepositoryImpl

class CheckSessionUseCase(
    private val sessionRepo: SessionRepositoryImpl
) {
    operator fun invoke(): Boolean =
        sessionRepo.getToken() != null && sessionRepo.isRememberMe()
}