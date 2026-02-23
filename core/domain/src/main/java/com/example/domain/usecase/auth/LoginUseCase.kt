package com.example.domain.usecase.auth

import com.example.common.resource.Resource
import com.example.domain.repository.UserPreferencesRepository
import com.example.domain.repository.UserSessionRepository
import com.example.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val userSessionRepository: UserSessionRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Flow<Resource<String>> {
        return repository.login(email, password).onEach { resource ->
            if (resource is Resource.Success) {
                val userId = resource.data
                userPreferencesRepository.clearOnlyDailyFoodLogs()//es ukve wavshale meored vamateb
                userPreferencesRepository.setCurrentUserId(userId)
            }
        }
    }
}