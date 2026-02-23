package com.example.domain.usecase.user_session

import com.example.domain.repository.UserSessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNameUseCase @Inject constructor(
    private val userSessionRepository: UserSessionRepository
) {
    fun invoke(): Flow<String?> {
        return userSessionRepository.getNameFlow()
    }
}