package com.example.domain.usecase.auth

import com.example.common.resource.Resource
import com.example.domain.repository.user_preferences.UserPreferencesRepository
import com.example.domain.repository.user_session.UserSessionRepository
import com.example.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
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
    ): Flow<Resource<String>> {
        return repository.register(email, password, rememberMe).onEach { resource ->
            if (resource is Resource.Success) {
                val newUserId = resource.data
                val previousUserId = userPreferencesRepository.getCurrentUserId().first()

                if (previousUserId != newUserId) {
                    userPreferencesRepository.clearOnlyDailyFoodLogs()
                }

                userSessionRepository.saveSession(token = "", rememberMe)
                userSessionRepository.saveName(name)
                userSessionRepository.saveEmail(email)
                userPreferencesRepository.setCurrentUserId(newUserId)
            }
        }
    }
}