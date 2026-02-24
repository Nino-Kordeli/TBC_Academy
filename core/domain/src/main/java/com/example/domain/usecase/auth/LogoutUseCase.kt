package com.example.domain.usecase.auth

import com.example.domain.repository.user_preferences.UserPreferencesRepository
import com.example.domain.repository.user_session.UserSessionRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userSessionRepository: UserSessionRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke() {
        userPreferencesRepository.setCurrentUserId(null)
        userSessionRepository.logout()
    }
}