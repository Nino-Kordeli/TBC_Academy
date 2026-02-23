package com.example.domain.usecase.auth

import com.example.domain.repository.UserPreferencesRepository
import com.example.domain.repository.UserSessionRepository
import com.example.domain.repository.auth.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val userSessionRepository: UserSessionRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        rememberMe: Boolean,
        name: String
    ) {
        val userId = repository.register(email, password, rememberMe)

        userSessionRepository.saveSession(token = "", rememberMe)
        userSessionRepository.saveName(name)
        userSessionRepository.saveEmail(email)

        userPreferencesRepository.clearOnlyDailyFoodLogs()
        userPreferencesRepository.setCurrentUserId(userId)
    }
}