package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.repository.SessionRepository

class LogoutUseCase(private val sessionRepository: SessionRepository) {
    suspend operator fun invoke() {
        sessionRepository.clearAll()
    }
}