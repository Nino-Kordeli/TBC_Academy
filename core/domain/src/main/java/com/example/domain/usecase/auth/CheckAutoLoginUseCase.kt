package com.example.domain.usecase.auth

import com.example.domain.repository.user_session.UserSessionRepository
import javax.inject.Inject

class CheckAutoLoginUseCase @Inject constructor(
    private val userSessionRepository: UserSessionRepository
) {
    suspend operator fun invoke(): Boolean {
        val remember = userSessionRepository.getRememberMe()
        val token = userSessionRepository.getToken()

        return remember && !token.isNullOrBlank()
    }
}