package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.data.repository.SessionRepositoryImpl

class CheckSessionUseCase(
    private val sessionRepo: SessionRepositoryImpl
) {
    suspend operator fun invoke(): Boolean {
        val token = sessionRepo.readToken()
        val rememberMe = sessionRepo.isRememberMe()
        return token != null && rememberMe
    }
}