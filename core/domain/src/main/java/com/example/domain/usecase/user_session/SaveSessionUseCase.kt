package com.example.domain.usecase.user_session

import com.example.domain.repository.UserSessionRepository
import javax.inject.Inject

class SaveSessionUseCase @Inject constructor(
    private val sessionRepository: UserSessionRepository
) {
    suspend operator fun invoke(token: String, rememberMe: Boolean) {
        sessionRepository.saveSession(token, rememberMe)
    }
}