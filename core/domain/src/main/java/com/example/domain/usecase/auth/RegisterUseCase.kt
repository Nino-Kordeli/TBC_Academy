package com.example.domain.usecase.auth

import com.example.domain.repository.UserPreferencesRepository
import com.example.domain.repository.UserSessionRepository
import com.example.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.first
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
        val previousUserId = userPreferencesRepository.getCurrentUserId().first()
        val userId = repository.register(email, password, rememberMe)

        if (previousUserId != userId) {
            userPreferencesRepository.clearOnlyDailyFoodLogs()
        }

        userSessionRepository.saveSession(token = "", rememberMe)
        userSessionRepository.saveName(name)
        userSessionRepository.saveEmail(email)
        userPreferencesRepository.setCurrentUserId(userId)
    }
}