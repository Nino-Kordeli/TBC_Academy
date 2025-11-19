package com.example.tbcacademy.domain.usecase

import com.example.tbcacademy.domain.repository.SessionRepository
import javax.inject.Inject

class CheckSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(): Boolean =
        sessionRepository.readToken() != null && sessionRepository.isRememberMe()
}
