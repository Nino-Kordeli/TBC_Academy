package com.example.domain.usecase.auth

import com.example.domain.repository.UserPreferencesRepository
import com.example.domain.repository.UserSessionRepository
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